package com.darkyellowcat.weconnected.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置类
 * 用于配置 BCrypt 密码编码器和安全策略
 *
 * @author darkyellowcat
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 配置 BCrypt 密码编码器 Bean
     * BCrypt 是一种安全的密码哈希函数，具有盐值自动管理功能
     *
     * @return PasswordEncoder 实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置安全过滤链
     * 禁用 CSRF 和默认登录页面，使用自定义的会话验证
     *
     * @param http HttpSecurity 对象
     * @return SecurityFilterChain 实例
     * @throws Exception 配置异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF（因为使用的是 Session + Cookie）
            .csrf(csrf -> csrf.disable())
            // 配置授权规则
            .authorizeHttpRequests(auth -> auth
                // 所有请求都允许访问（通过自定义拦截器验证登录状态）
                .anyRequest().permitAll()
            );
        
        return http.build();
    }
}
