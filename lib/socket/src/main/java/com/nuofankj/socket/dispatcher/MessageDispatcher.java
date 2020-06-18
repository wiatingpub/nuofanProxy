package com.nuofankj.socket.dispatcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuofankj.socket.dispatcher.bean.MessageBean;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.proto.AbstractMessage;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xifanxiaxue
 * @date 2020/6/2 8:13
 * @desc 协议分发
 */
@Slf4j
public class MessageDispatcher {

    /**
     * 协议id->MessageBean
     */
    public static Map<Integer, MessageBean> id2MessageBeanMap = new HashMap<>();

    public static void dispatch(String message, ChannelSession channelSession) {
        JSONObject json = JSONObject.parseObject(message);
        int code = json.getInteger("code");
        MessageBean messageBean = MessageDispatcher.id2MessageBeanMap.get(code);
        if (messageBean == null) {
            log.error("协议号:{}找不到分发对象", code);
        } else {
            AbstractMessage abstractMessage = JSON.parseObject(message, messageBean.getMessage());
            Method targetMethod = messageBean.getTargetMethod();
            targetMethod.setAccessible(true);
            try {
                targetMethod.invoke(messageBean.getTargetObject(), channelSession, abstractMessage);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
