package com.darkyellowcat.weconnected.once.importuser;

import com.darkyellowcat.weconnected.mapper.UserMapper;
import com.darkyellowcat.weconnected.model.domain.User;
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
//    @Scheduled(initialDelay = 5000, fixedRate = Long.MAX_VALUE)
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        System.out.println("goodgoodgood");
        stopWatch.start();
        final int INSERT_NUM = 1000;
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("wangwu" + i);
            user.setUserAccount("fakewangwu" + i);
            user.setAvatarUrl("https://image.baidu.com/search/detail?adpicid=0&b_applid=9706955638790096030&bdtype=0&commodity=&copyright=&cs=4023030683%2C3088022882&di=7552572858984038401&fr=click-pic&fromurl=http%253A%252F%252Fbaike.baidu.com%252Fitem%252F%2525E7%252599%2525BE%2525E5%2525BA%2525A6%2525E6%252590%25259C%2525E7%2525B4%2525A2%2525E9%2525A3%25258E%2525E4%2525BA%252591%2525E6%2525A6%25259C%252F1279567&gsm=1e&hd=&height=0&hot=&ic=&ie=utf-8&imgformat=&imgratio=&imgspn=0&is=0%2C0&isImgSet=&latest=&lid=ec57fda3000b5c1e&lm=&objurl=https%253A%252F%252Fbkimg.cdn.bcebos.com%252Fpic%252F11385343fbf2b2119313eca125d272380cd79023209b&os=734494093%2C1023502814&pd=image_content&pi=0&pn=12&rn=1&simid=4023030683%2C3088022882&tn=baiduimagedetail&width=0&word=%E7%99%BE%E5%BA%A6%E7%83%AD%E6%90%9C&z=");
            user.setGender(0);
            user.setUserPassword("12345678");
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
