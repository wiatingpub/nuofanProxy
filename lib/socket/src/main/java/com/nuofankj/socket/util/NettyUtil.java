package com.nuofankj.socket.util;

import com.alibaba.fastjson.JSON;
import com.nuofankj.socket.dispatcher.MessageDispatcher;
import com.nuofankj.socket.dispatcher.bean.MessageBean;
import com.nuofankj.socket.proto.AbstractMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * @author xifanxiaxue
 * @date 2020/5/31 9:57
 * @desc netty辅助工具
 */
public class NettyUtil {

    /**
     * 解析AbstractMessage
     *
     * @param in
     * @return
     */
    public static AbstractMessage decodeMessage(ByteBuf in) {
        // 协议id
        int messageId = in.readInt();
        // 协议长度
        int messageLength = in.readInt();
        // 字节流数据
        byte[] contentBytes = new byte[messageLength];
        in.readBytes(contentBytes);
        // 根据协议id读取对应协议
        MessageBean messageBean = MessageDispatcher.id2MessageBeanMap.get(messageId);
        if (messageBean == null) {
            return null;
        }
        
        return JSON.parseObject(contentBytes, messageBean.getMessage());
    }

    /**
     * 对协议进行编码
     *
     * @param message
     * @param out
     */
    public static void encodeMessage(AbstractMessage message, ByteBuf out) {
        byte[] bytes = JSON.toJSONBytes(message);
        out.writeInt(message.getCode());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }

    /**
     * 获取Channel的远程IP地址
     *
     * @param channel
     * @return
     */
    public static String parseChannelRemoteAddress(Channel channel) {
        if (null == channel) {
            return "";
        }

        SocketAddress remote = channel.remoteAddress();
        final String address = remote != null ? remote.toString() : "";

        if (address.length() <= 0) {
            return "";
        }

        int index = address.lastIndexOf("/");
        if (index >= 0) {
            return address.substring(index + 1);
        }
        return address;
    }
}
