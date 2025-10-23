package com.darkyellowcat.weconnected.service;

import com.darkyellowcat.weconnected.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 用户服务测试
 *
 * @author darkyellowcat
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("ddd");
        user.setUserAccount("123");
        user.setAvatarUrl("https://assets.leetcode.cn/aliyun-lc-upload/users/KFdLX8Kdka/avatar_1726367031.png");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("dogdarkyellowcat");
        user.setUserAccount("123");
        // 侵刪
        user.setAvatarUrl("https://image.baidu.com/search/detail?adpicid=0&b_applid=9706955638790096030&bdtype=0&commodity=&copyright=&cs=4023030683%2C3088022882&di=7552572858984038401&fr=click-pic&fromurl=http%253A%252F%252Fbaike.baidu.com%252Fitem%252F%2525E7%252599%2525BE%2525E5%2525BA%2525A6%2525E6%252590%25259C%2525E7%2525B4%2525A2%2525E9%2525A3%25258E%2525E4%2525BA%252591%2525E6%2525A6%25259C%252F1279567&gsm=1e&hd=&height=0&hot=&ic=&ie=utf-8&imgformat=&imgratio=&imgspn=0&is=0%2C0&isImgSet=&latest=&lid=ec57fda3000b5c1e&lm=&objurl=https%253A%252F%252Fbkimg.cdn.bcebos.com%252Fpic%252F11385343fbf2b2119313eca125d272380cd79023209b&os=734494093%2C1023502814&pd=image_content&pi=0&pn=12&rn=1&simid=4023030683%2C3088022882&tn=baiduimagedetail&width=0&word=%E7%99%BE%E5%BA%A6%E7%83%AD%E6%90%9C&z=");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    public void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    void userRegister() {
        String userAccount = "darkyellowcat";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "darkyellowcat";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "cat";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "dogdarkyellowcat";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "darkyellowcat";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testSearchUsersByTags() {
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> userList = userService.searchUsersByTags(tagNameList);
        Assert.assertNotNull(userList);
    }
}