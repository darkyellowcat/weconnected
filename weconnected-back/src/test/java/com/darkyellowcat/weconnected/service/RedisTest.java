package com.darkyellowcat.weconnected.service;

import com.darkyellowcat.weconnected.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * Redis 测试
 *
 * @author darkyellowcat
 */
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 增
        valueOperations.set("darkyellowcatString", "dog");
        valueOperations.set("darkyellowcatInt", 1);
        valueOperations.set("darkyellowcatDouble", 2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("darkyellowcat");
        valueOperations.set("darkyellowcatUser", user);
        // 查
        Object darkyellowcat = valueOperations.get("darkyellowcatString");
        Assertions.assertTrue("dog".equals((String) darkyellowcat));
        darkyellowcat = valueOperations.get("darkyellowcatInt");
        Assertions.assertTrue(1 == (Integer) darkyellowcat);
        darkyellowcat = valueOperations.get("darkyellowcatDouble");
        Assertions.assertTrue(2.0 == (Double) darkyellowcat);
        System.out.println(valueOperations.get("darkyellowcatUser"));
        valueOperations.set("darkyellowcatString", "dog");
        redisTemplate.delete("darkyellowcatString");
    }
}
