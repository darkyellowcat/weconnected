package com.darkyellowcat.weconnected.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天消息实体
 *
 * @author darkyellowcat
 */
@Data
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息类型：JOIN（加入）、CHAT（聊天）、LEAVE（离开）
     */
    private String type;

    /**
     * 发送者用户ID
     */
    private Long senderId;

    /**
     * 发送者用户名
     */
    private String senderName;

    /**
     * 发送者头像
     */
    private String senderAvatar;

    /**
     * 队伍ID
     */
    private Long teamId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date timestamp;

    public ChatMessage() {
        this.timestamp = new Date();
    }

    public ChatMessage(String type, Long senderId, String senderName, String senderAvatar, Long teamId, String content) {
        this.type = type;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderAvatar = senderAvatar;
        this.teamId = teamId;
        this.content = content;
        this.timestamp = new Date();
    }
}
