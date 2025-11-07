package com.darkyellowcat.weconnected.once.importuser;

import com.darkyellowcat.weconnected.mapper.UserMapper;
import com.darkyellowcat.weconnected.model.domain.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

/**
 * 插入用户数据
 *
 * @author darkyellowcat
 */
@Component
public class InsertUsers {

    @Resource
    private UserMapper userMapper;

    /**
     * 批量插入用户
     */
    @Scheduled(initialDelay = 500, fixedRate = Long.MAX_VALUE)
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        System.out.println("goodgoodgood");
        stopWatch.start();
        final int INSERT_NUM = 1000;
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("wangwu" + i);
            user.setUserAccount("fakewangwu" + i);
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setUserRole(0);
            user.setAvatarUrl("wangwu" + i);
            user.setPhone("123");
            user.setEmail("123@qq.com");
            user.setTags("[]");
            user.setUserStatus(0);
            user.setUserRole(0);
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
