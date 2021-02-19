package com.example.demo.service.impl;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.common.constant.SocketConstants;
import com.example.common.constant.SocketConstants.*;
import com.example.common.util.StringUtil;
import com.example.demo.bo.SocketMessage;
import com.example.demo.bo.SocketUserInfo;
import com.example.demo.service.ISocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by new on 2021/2/3.
 *
 *  风险：
 *      1.多服务部署的情况保证连接不乱
 *      2.ip问题，web写固定ip地址？
 *
 *  问题：
 *      1.想办法获取游客一段时间的固定id
 *
 *  已知bug：
 */
@Service
public class SocketServiceImpl extends BaseService implements ISocketService {

    @Autowired
    private SocketIOServer socketIoServer;

    private static Map<String, SocketIOClient> customerClientMap = new ConcurrentHashMap<>(); // 客户接入 id -> client
    private static Map<String, SocketIOClient> serviceClientMap  = new ConcurrentHashMap<>(); // 客服接入 id -> client

    private static List<String>        aloneCustomer = new Vector<>(); // 未匹配的客户 id
    private static Map<String, String> CSRel         = new ConcurrentHashMap<>(); // 客户客服匹配关系, id -> id

    @Override
    public void onConnect(SocketIOClient client) {
        SocketUserInfo userInfo = this.getUserInfo(client);

        // 1)连接参数验证
        if (StringUtil.isEmpty(userInfo.getUserType()) || StringUtil.isEmpty(userInfo.getUserId())) {
            client.sendEvent(MsgType.INFO, "参数错误，连接失败！");
            logger.info("客户端:{} 参数不全，拒绝连接！{}", client.getSessionId().toString(), userInfo.toString());
            client.disconnect();
            return;
        }
        if (customerClientMap.containsKey(userInfo.getId()) || serviceClientMap.containsKey(userInfo.getId())) {
            client.sendEvent(MsgType.INFO, "该账号已连接！请勿重复登录");
            logger.info("客户端:{} 重复登录，拒绝连接！{}", client.getSessionId().toString(), userInfo.toString());
            client.disconnect();
            return;
        }
        logger.info("客户端:{} 已连接。{}", client.getSessionId().toString(), userInfo.toString());

        /**
         * 2)接入客服系统
         *      客户接入：将客户加入未匹配池,触发一次匹配
         *      客服接入：触发一次匹配
         */
        if (RoleType.CUST.equals(userInfo.getRoleType())) {
            // 用户接入事件
            customerClientMap.put(userInfo.getId(), client);
            client.sendEvent(MsgType.INFO, "客户接入客服系统成功。");
            aloneCustomer.add(userInfo.getId());
        } else {
            // 客服接入事件
            serviceClientMap.put(userInfo.getId(), client);
            client.sendEvent(MsgType.INFO, "客服接入客服系统成功。");
        }

        // 触发一次匹配(放在最后，等所有关系都处理完毕后再匹配)
        this.matching();
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        SocketUserInfo userInfo = this.getUserInfo(client);
        String sessionId = client.getSessionId().toString();
        logger.info("客户端:{} 断开连接！{}", sessionId, userInfo.toString());

        // 服务端主动断开的连接不需要清除连接关系
        if (StringUtil.isEmpty(userInfo.getUserType()) || StringUtil.isEmpty(userInfo.getUserId())) {
            // 无效连接
            return;
        }
        if (customerClientMap.containsKey(userInfo.getId()) && !customerClientMap.get(userInfo.getId()).getSessionId().toString().equals(sessionId)) {
            // 防止重复登录把原有的连接删了
            return;
        }
        if (serviceClientMap.containsKey(userInfo.getId()) && !serviceClientMap.get(userInfo.getId()).getSessionId().toString().equals(sessionId)) {
            // 防止重复登录把原有的连接删了
            return;
        }

        // 未接入客服的客户退出
        if (aloneCustomer.contains(userInfo.getId())) {
            aloneCustomer.remove(userInfo.getId());
        }

        // 客户退出,告知客服客户已经退出
        if (CSRel.containsKey(userInfo.getId())) {
            SocketIOClient staffCl = serviceClientMap.get(CSRel.get(userInfo.getId()));
            staffCl.sendEvent(MsgType.INFO, "客户<" + userInfo.getUserName() + ">已经退出连接！");
            CSRel.remove(userInfo.getId());
        }

        // 客服退出,通知相关客户客服已经退出,将相关客户归入未匹配池
        if (CSRel.containsValue(userInfo.getId())) {
            List<String> custList = new ArrayList<>();
            CSRel.keySet().forEach(key -> {
                String value = CSRel.get(key);
                if (value.equals(userInfo.getId())) {
                    custList.add(key);
                }
            });
            custList.forEach(item -> {
                customerClientMap.get(item).sendEvent(MsgType.INFO, "客服已经退出连接！");
                aloneCustomer.add(item);
                CSRel.remove(item);
            });
        }

        // 客户客服退出
        if (customerClientMap.containsKey(userInfo.getId())) {
            customerClientMap.remove(userInfo.getId());
        }
        if (serviceClientMap.containsKey(userInfo.getId())) {
            serviceClientMap.remove(userInfo.getId());
        }

        // 触发一次匹配(放在最后，等所有关系都处理完毕后再匹配)
        this.matching();
    }

    @Override
    public void onEvent(SocketIOClient client, AckRequest request, SocketMessage data) {
        SocketUserInfo userInfo = this.getUserInfo(client);

        logger.info("<{}>发来消息：{}", userInfo.getUserName(), data);
        if (RoleType.CUST.equals(userInfo.getRoleType())) {
            if (CSRel.containsKey(userInfo.getId())) {
                String kefuId = CSRel.get(userInfo.getId());
                SocketIOClient keFuclient = serviceClientMap.get(kefuId);
                SocketUserInfo kefuInfo = this.getUserInfo(keFuclient);
                if (kefuInfo != null) {
                    data.setMessageTime(new Date());
                    data.setSenderId(userInfo.getId());
                    data.setSenderName(userInfo.getUserName());
                    data.setReceiverId(kefuId);
                    data.setReceiverName(kefuInfo.getUserName());
                    keFuclient.sendEvent(MsgType.MSG, data);
                }
            }
        } else {
            String targetCust = data.getReceiverId();
            SocketUserInfo targetCustInfo = this.getUserInfoById(targetCust);
            if (targetCustInfo != null) {
                data.setMessageTime(new Date());
                data.setSenderId(userInfo.getId());
                data.setSenderName(userInfo.getUserName());
                data.setReceiverId(targetCust);
                data.setReceiverName(targetCustInfo.getUserName());
                customerClientMap.get(targetCust).sendEvent(MsgType.MSG, data);
            } else {
                // 无目标则群发
                data.setMessageTime(new Date());
                data.setSenderId(userInfo.getId());
                data.setSenderName(userInfo.getUserName());
                for (SocketIOClient cl : customerClientMap.values()) {
                    cl.sendEvent(MsgType.MSG, data);
                }
            }
        }
    }

    /**
     * 客户客服匹配
     */
    private void matching() {
        if (serviceClientMap.isEmpty()) {
            aloneCustomer.forEach(custId -> {
                if (customerClientMap.containsKey(custId)) {
                    SocketIOClient custClient = customerClientMap.get(custId);
                    custClient.sendEvent(MsgType.INFO, "请等待客服接入!");
                }
            });
            return;
        }

        /**
         * 匹配规则:
         *  1。客户优先连接历史客服（不能超过该客服的最大限制）
         *  2。其次匹配当前处理客户最少的客服（不能超过该客服的最大限制）
         *  3。想所有未匹配客户发送消息，告知等待人数
         */
        int answerLimit = 3; // 客服应答数限制
        while (true) {
            if (aloneCustomer.isEmpty()) {
                break;
            }
            String custId = aloneCustomer.get(0);
            String kefuId = this.getKefu(answerLimit, custId);
            if (StringUtil.isNotEmpty(kefuId)) {
                // 重新设置关系
                aloneCustomer.remove(custId);
                CSRel.put(custId, kefuId);

                // 向客户客服双方发送消息
                SocketIOClient kefu = serviceClientMap.get(kefuId);
                SocketIOClient cust = customerClientMap.get(custId);
                cust.sendEvent(MsgType.INFO, "客服<" + this.getUserInfo(kefu).getUserName() + ">已经接入。");
                kefu.sendEvent(MsgType.INFO, "客户<" + this.getUserInfo(cust).getUserName() + ">已经接入，请及时答复问题。");
            } else {
                break;
            }
        }

        // 匹配完后给还未匹配的客户发送等待消息
        if (!aloneCustomer.isEmpty()) {
            aloneCustomer.forEach(custId -> {
                if (customerClientMap.containsKey(custId)) {
                    SocketIOClient custClient = customerClientMap.get(custId);
                    custClient.sendEvent(MsgType.INFO, "当前排队人数" + aloneCustomer.size() + "人，请等待客服接入！");
                }
            });
        }

        // 匹配完后如果有客服，没客户。这告知客服当前无客户
        if (!serviceClientMap.isEmpty() && customerClientMap.isEmpty()) {
            serviceClientMap.values().forEach(cl -> {
                cl.sendEvent(MsgType.INFO, "当前客服接入数" + serviceClientMap.size() + "，客户接入数" + customerClientMap.size() + "。");
            });
        }
    }

    private String getKefu(int answerLimit, String kehuId) {
        if (StringUtil.isEmpty(kehuId)) {
            return "";
        }
        Map<String, Integer> serviceCounts = new HashMap<>();
        serviceClientMap.keySet().forEach(item -> {
            serviceCounts.put(item, 0);
        });
        CSRel.values().forEach(item -> {
            if (serviceCounts.containsKey(item)) {
                serviceCounts.put(item, serviceCounts.get(item) + 1);
            }
        });

        int minCount = 0;
        String minKefu = "";
        for (String item : serviceCounts.keySet()) {
            int count = serviceCounts.get(item);
            if (isEmpty(minKefu) || minCount > count) {
                minKefu = item;
                minCount = count;
            }
        }
        logger.info("当前处理客户数量最少的客服是{},处理量为{}", minKefu, minCount);
        if (minCount >= answerLimit) {
            return "";
        }
        return minKefu;
    }

    private SocketUserInfo getUserInfo(SocketIOClient client) {
        SocketUserInfo socketUserInfo = new SocketUserInfo();
        socketUserInfo.setUserType(client.getHandshakeData().getSingleUrlParam("userType"));
        socketUserInfo.setUserId(client.getHandshakeData().getSingleUrlParam("userId"));
        socketUserInfo.setTokenId(client.getHandshakeData().getSingleUrlParam("tokenId"));
        socketUserInfo.setChannelType(client.getHandshakeData().getSingleUrlParam("channelType"));
        socketUserInfo.setRoleType(client.getHandshakeData().getSingleUrlParam("roleType"));
        socketUserInfo.setAccNum(client.getHandshakeData().getSingleUrlParam("accNum"));
        socketUserInfo.setUserName(client.getHandshakeData().getSingleUrlParam("userName"));

        socketUserInfo.setId(socketUserInfo.getUserType() + SocketConstants.SPLIT + socketUserInfo.getUserId());
        if (StringUtil.isEmpty(socketUserInfo.getUserName())) {
            if (StringUtil.isNotEmpty(socketUserInfo.getAccNum())) {
                socketUserInfo.setUserName(socketUserInfo.getAccNum());
            } else {
                socketUserInfo.setUserName(socketUserInfo.getUserId());
            }
        }
        return socketUserInfo;
    }

    private SocketUserInfo getUserInfoById(String id) {
        if (StringUtil.isEmpty(id)) {
            return null;
        }
        if (customerClientMap.containsKey(id)) {
            SocketIOClient client = customerClientMap.get(id);
            return this.getUserInfo(client);
        } else if (serviceClientMap.containsKey(id)) {
            SocketIOClient client = serviceClientMap.get(id);
            return this.getUserInfo(client);
        }
        return null;
    }

    public String printStatus() {
        String msg = "当前连接统计.";
        msg += "\n当前所有连接为：" + socketIoServer.getAllClients().size();
        msg += "\n接入客户数量：" + customerClientMap.size();
        msg += "\n接入客服数量：" + serviceClientMap.size();
        msg += "\n等待队列中的客户数量：" + aloneCustomer.size();
        for (String key : CSRel.keySet()) {
            msg += "\n客户:" + key + "正在连接的客服:" + CSRel.get(key);
        }
        logger.info(msg);
        return msg;
    }
}
