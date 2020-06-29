package com.nuofankj.socket.handler;

import com.nuofankj.socket.proto.AbstractMessage;
import com.nuofankj.socket.util.NettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xifanxiaxue
 * @date 2020/6/29 8:06
 * @desc
 */
public class MessageEncoder extends MessageToByteEncoder<AbstractMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, ByteBuf out) throws Exception {
        NettyUtil.encodeMessage(msg, out);
    }
}
