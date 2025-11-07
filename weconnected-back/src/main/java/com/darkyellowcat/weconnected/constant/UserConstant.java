package com.darkyellowcat.weconnected.constant;

/**
 * 用户常量
 *
 * @author darkyellowcat
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";

    //  ------- 权限 --------

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;

    //  ------- 注册默认模板值 --------

    /**
     * 默认头像地址
     */
    String DEFAULT_AVATAR_URL = "https://static.weconnected.cn/assets/avatar/default.png";

    /**
     * 默认个性签名
     */
    String DEFAULT_PROFILE = "这个人很懒，什么都没有写~";

    /**
     * 默认标签（JSON 字符串）
     */
    String DEFAULT_TAGS_JSON = "[]";

    /**
     * 默认性别（0：保密/未知）
     */
    int DEFAULT_GENDER = 0;

    //  ------- 头像提供方配置 --------

    /**
     * 头像提供方：dicebear | ui-avatars
     */
    String AVATAR_PROVIDER = "dicebear";

    /**
     * DiceBear 模板（7.x），可替换风格如 bottts-neutral、identicon 等
     */
    String DICEBEAR_URL_TEMPLATE = "https://api.dicebear.com/7.x/bottts-neutral/svg?seed=%s";

    /**
     * UI Avatars 模板
     */
    String UI_AVATARS_URL_TEMPLATE = "https://ui-avatars.com/api/?name=%s&background=random&size=256";

}
