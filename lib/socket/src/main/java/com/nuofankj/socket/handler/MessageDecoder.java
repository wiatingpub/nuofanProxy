package com.nuofankj.socket.handler;

import com.nuofankj.socket.dispatcher.MessageDispatcher;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.manager.SessionManager;
import com.nuofankj.socket.proto.AbstractMessage;
import com.nuofankj.socket.util.NettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author xifanxiaxue
 * @date 2020/6/28 22:42
 * @desc 协议解析
 */
@Slf4j
public class MessageDecoder extends ReplayingDecoder<AbstractMessage> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        AbstractMessage abstractMessage = NettyUtil.decodeMessage(in);
        if (abstractMessage == null) {
            return;
        }
        
        ChannelSession channelSession = SessionManager.getChannelSession(ctx.channel());
        MessageDispatcher.dispatch(abstractMessage, channelSession);
    }
}
