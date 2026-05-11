package com.darkyellowcat.weconnected.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.darkyellowcat.weconnected.common.BaseResponse;
import com.darkyellowcat.weconnected.common.ErrorCode;
import com.darkyellowcat.weconnected.common.ResultUtils;
import com.darkyellowcat.weconnected.constant.UserConstant;
import com.darkyellowcat.weconnected.exception.BusinessException;
import com.darkyellowcat.weconnected.model.domain.ChatMessage;
import com.darkyellowcat.weconnected.model.domain.User;
import com.darkyellowcat.weconnected.model.domain.UserTeam;
import com.darkyellowcat.weconnected.service.UserService;
import com.darkyellowcat.weconnected.service.UserTeamService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private UserTeamService userTeamService;

    private final Gson gson = new Gson();

    @MessageMapping("/team/join")
    public void joinTeamChat(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        User loginUser = getUserFromSession(headerAccessor);
        if (loginUser == null) {
            return;
        }

        ChatMessage joinMessage = new ChatMessage();
        joinMessage.setType("JOIN");
        joinMessage.setSenderId(loginUser.getId());
        joinMessage.setSenderName(loginUser.getUsername());
        joinMessage.setSenderAvatar(loginUser.getAvatarUrl());
        joinMessage.setTeamId(chatMessage.getTeamId());
        joinMessage.setContent(loginUser.getUsername() + " 加入了聊天室");

        publishToRedis(joinMessage);
    }

    @MessageMapping("/team/send")
    public void sendTeamMessage(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        User loginUser = getUserFromSession(headerAccessor);
        if (loginUser == null) {
            return;
        }

        if (chatMessage.getContent() == null || chatMessage.getContent().trim().isEmpty()) {
            return;
        }
        if (chatMessage.getContent().length() > 500) {
            return;
        }

        ChatMessage message = new ChatMessage();
        message.setType("CHAT");
        message.setSenderId(loginUser.getId());
        message.setSenderName(loginUser.getUsername());
        message.setSenderAvatar(loginUser.getAvatarUrl());
        message.setTeamId(chatMessage.getTeamId());
        message.setContent(chatMessage.getContent());

        publishToRedis(message);
        saveMessageToRedis(message);
    }

    @MessageMapping("/team/leave")
    public void leaveTeamChat(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        User loginUser = getUserFromSession(headerAccessor);
        if (loginUser == null) {
            return;
        }

        ChatMessage leaveMessage = new ChatMessage();
        leaveMessage.setType("LEAVE");
        leaveMessage.setSenderId(loginUser.getId());
        leaveMessage.setSenderName(loginUser.getUsername());
        leaveMessage.setSenderAvatar(loginUser.getAvatarUrl());
        leaveMessage.setTeamId(chatMessage.getTeamId());
        leaveMessage.setContent(loginUser.getUsername() + " 离开了聊天室");

        publishToRedis(leaveMessage);
    }

    @GetMapping("/history")
    @org.springframework.web.bind.annotation.ResponseBody
    public BaseResponse<List<ChatMessage>> getChatHistory(@RequestParam Long teamId, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", loginUser.getId());
        queryWrapper.eq("teamId", teamId);
        long count = userTeamService.count(queryWrapper);
        if (count <= 0) {
            throw new BusinessException(ErrorCode.NO_AUTH, "您不是该团队成员");
        }
        String key = "weconnected:chat:history:team:" + teamId;
        List<Object> rawMessages = redisTemplate.opsForList().range(key, 0, -1);
        if (rawMessages == null || rawMessages.isEmpty()) {
            return ResultUtils.success(new ArrayList<>());
        }
        List<ChatMessage> messages = new ArrayList<>();
        for (Object raw : rawMessages) {
            messages.add(gson.fromJson(raw.toString(), ChatMessage.class));
        }
        Collections.reverse(messages);
        return ResultUtils.success(messages);
    }

    private User getUserFromSession(SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes == null) {
            return null;
        }
        return (User) sessionAttributes.get(UserConstant.USER_LOGIN_STATE);
    }

    private void publishToRedis(ChatMessage message) {
        try {
            String channel = "weconnected:chat:team:" + message.getTeamId();
            String messageJson = gson.toJson(message);
            redisTemplate.convertAndSend(channel, messageJson);
        } catch (Exception e) {
            log.error("发布消息到 Redis 失败", e);
        }
    }

    private void saveMessageToRedis(ChatMessage message) {
        try {
            String key = "weconnected:chat:history:team:" + message.getTeamId();
            String messageJson = gson.toJson(message);
            redisTemplate.opsForList().leftPush(key, messageJson);
            redisTemplate.opsForList().trim(key, 0, 99);
            redisTemplate.expire(key, 7, TimeUnit.DAYS);
        } catch (Exception e) {
            log.error("保存消息到 Redis 失败", e);
        }
    }
}
