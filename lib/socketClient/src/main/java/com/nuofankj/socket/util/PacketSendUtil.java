package com.nuofankj.socket.util;

import com.nuofankj.socket.proto.AbstractMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xifanxiaxue
 * @date 2020/6/3 8:09
 * @desc 协议推送工具
 */
public class PacketSendUtil {

    /**
     * 给主服推送协议
     *
     * @param ctx
     * @param message
     */
    public static void sendMessage(ChannelHandlerContext ctx, AbstractMessage message) {
        ctx.writeAndFlush(message);
    }
}
