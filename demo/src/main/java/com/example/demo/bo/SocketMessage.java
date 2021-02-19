package com.example.demo.bo;

import lombok.Data;

import java.util.Date;

/**
 * Created by new on 2021/1/27.
 */
@Data
public class SocketMessage {
    private Date messageTime; // 消息

    private String senderName; // 发送人
    private String senderId;
    private String receiverName; // 接收人
    private String receiverId;

    private String msgContent; // 消息内容
}
