package com.example.demo.bo;

import lombok.Data;

/**
 * Created by new on 2021/1/29.
 */
@Data
public class SocketUserInfo {

    // userType + @@ + userId
    private String id;

    // 用户类型：游客youke，客服kefu，客户kehu，微信weixin
    private String userType;

    // 标识含义：游客临时id，客服工号id，客户id，微信id
    private String userId;

    // 登录鉴权信息，客服登录需要核实
    private String tokenId;

    // 接入渠道：客服系统kefu，销售页面kehu，微信weixin
    private String channelType;

    // 角色：客服kefu 客户kehu
    private String roleType;

    // 接入号
    private String accNum;

    // 用户名称
    private String userName;
}
