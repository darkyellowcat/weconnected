package com.darkyellowcat.weconnected.config;

import com.darkyellowcat.weconnected.model.domain.ChatMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Redis 消息监听器
 * 接收 Redis 发布的聊天消息，并通过 WebSocket 广播给客户端
 *
 * @author darkyellowcat
 */
@Slf4j
@Component
public class RedisMessageListener implements MessageListener {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    private final Gson gson = new Gson();

    /**
     * 处理 Redis 消息
     *
     * @param message Redis 消息
     * @param pattern 匹配模式
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // 解析消息
            String messageBody = new String(message.getBody());
            ChatMessage chatMessage = gson.fromJson(messageBody, ChatMessage.class);
            
            log.info("收到 Redis 广播消息: teamId={}, senderId={}, type={}", 
                    chatMessage.getTeamId(), chatMessage.getSenderId(), chatMessage.getType());
            
            // 通过 WebSocket 广播消息到对应的队伍频道
            String destination = "/topic/team/" + chatMessage.getTeamId();
            messagingTemplate.convertAndSend(destination, chatMessage);
            
            log.info("消息已广播到: {}", destination);
        } catch (Exception e) {
            log.error("处理 Redis 消息失败", e);
        }
    }
}
