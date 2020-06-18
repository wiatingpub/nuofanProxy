package com.nuofankj.socket.handler;

import com.alibaba.fastjson.JSONObject;
import com.nuofankj.socket.dispatcher.MessageDispatcher;
import com.nuofankj.socket.event.LogoutEvent;
import com.nuofankj.socket.manager.ChannelManager;
import com.nuofankj.socket.manager.ChannelSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author xifanxiaxue
 * @date 2020/5/30 16:36
 * @desc
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private ApplicationEventPublisher applicationEventPublisher;

    public MessageHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        ChannelSession channelSession = ChannelManager.getChannelSession(ctx.channel());
        if (channelSession == null) {
            log.error("连接{}找不到对应session", ctx.channel());
            return;
        }

        MessageDispatcher.dispatch(frame.text(), channelSession);

        ctx.fireChannelRead(frame.retain());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ChannelSession channelSession = ChannelManager.removeChannel(ctx.channel());
        if (channelSession != null) {
            applicationEventPublisher.publishEvent(new LogoutEvent(this, channelSession.getNickName()));
        }

        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ChannelSession channelSession = ChannelManager.removeChannel(ctx.channel());
        if (channelSession != null) {
            applicationEventPublisher.publishEvent(new LogoutEvent(this, channelSession.getNickName()));
        }
    }
}
