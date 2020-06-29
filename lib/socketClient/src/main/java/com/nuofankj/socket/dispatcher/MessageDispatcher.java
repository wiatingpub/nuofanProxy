package com.nuofankj.socket.dispatcher;

import com.nuofankj.socket.dispatcher.bean.MessageBean;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.proto.AbstractMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xifanxiaxue
 * @date 2020/6/2 8:13
 * @desc 协议组件，作用是分发和解析协议
 */
@Slf4j
@Getter
public class MessageDispatcher {

    /**
     * 协议id->MessageBean
     */
    public static Map<Integer, MessageBean> id2MessageBeanMap = new HashMap<>();

    /**
     * 协议分发
     *
     * @param abstractMessage
     * @param channelSession
     */
    public static void dispatch(AbstractMessage abstractMessage, ChannelSession channelSession) {
        MessageBean messageBean = id2MessageBeanMap.get(abstractMessage.getCode());
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
