package com.example.common.constant;

/**
 * Created by new on 2021/2/4.
 */
public interface SocketConstants {

    public static final String SPLIT = "@@"; // 用户类型和标识之间的隔断符

    // 消息类型
    public static final class MsgType {
        public static final String MSG  = "message"; // 聊天消息
        public static final String INFO = "information"; // 提示
    }

    // 用户类型
    public static final class UserType {
        public static final String VIEW   = "youke"; // 游客号
        public static final String STAFF  = "kefu"; // 工号
        public static final String CUST   = "kehu"; // 客户号
        public static final String WECHAT = "weixin"; // 微信号
    }

    // 角色类型
    public static final class RoleType {
        public static final String SERV = "kefu"; // 客服
        public static final String CUST = "kehu"; // 客户
    }

    // 接入渠道
    public static final class ChannelType {
        public static final String TEL    = "TEL"; // 电话
        public static final String PC     = "PC"; // 用户端web
        public static final String APP    = "APP"; // 用户端APP
        public static final String WECHAT = "WECHAT"; // 微信
    }

    // 接入类型
    public static final class AccessType {
        public static final String USER = "1000"; // 用户
        public static final String VIEW = "1100"; // 游客
    }

}
