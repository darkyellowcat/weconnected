package com.darkyellowcat.weconnected.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

/**
 * Redis 消息监听容器配置
 * 用于订阅聊天消息频道
 *
 * @author darkyellowcat
 */
@Slf4j
@Configuration
public class RedisListenerConfig {

    @Resource
    private RedisMessageListener redisMessageListener;

    /**
     * 配置 Redis 消息监听容器
     *
     * @param connectionFactory Redis 连接工厂
     * @return 消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        
        // 订阅所有队伍聊天频道: weconnected:chat:team:*
        container.addMessageListener(redisMessageListener, new PatternTopic("weconnected:chat:team:*"));
        
        log.info("Redis 消息监听器已启动，订阅频道: weconnected:chat:team:*");
        
        return container;
    }
}
