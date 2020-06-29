package com.nuofankj.socket.server;

import com.nuofankj.socket.handler.HeartBeatHandler;
import com.nuofankj.socket.handler.MessageDecoder;
import com.nuofankj.socket.handler.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xifanxiaxue
 * @date 2/8/20
 * @desc
 */
@Slf4j
@Component
public class SocketServer implements SmartInitializingSingleton {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private NetServerOptions serverOptions;
    private EventLoopGroup workerGroup;
    private Channel channel;
    private Bootstrap bootstrap;
    @Value("${remote.port}")
    private int port;
    @Value("${remote.host}")
    private String host;

    @Override
    public void afterSingletonsInstantiated() {
        this.serverOptions = new NetServerOptions.Builder(port).build();
        this.start();
    }

    private void start() {
        Class<? extends ServerChannel> serverChannel;
        if (isLinux()) {
            this.workerGroup = new EpollEventLoopGroup(serverOptions.getAcceptorThreads(), new DefaultThreadFactory("NetServerWorkerIoThread"));
            serverChannel = EpollServerSocketChannel.class;
        } else {
            this.workerGroup = new NioEventLoopGroup(serverOptions.getAcceptorThreads(), new DefaultThreadFactory("NetServerWorkerIoThread"));
            serverChannel = NioServerSocketChannel.class;
        }

        bootstrap.group(workerGroup).channel(serverChannel)
                .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(createNetServerChannelInitializer());
        listen();
    }

    private ChannelInitializer<Channel> createNetServerChannelInitializer() {

        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) {
                ch.pipeline().addLast(new MessageDecoder());
                ch.pipeline().addLast(new MessageEncoder());
                ch.pipeline().addLast(new IdleStateHandler(60, 30, 0, TimeUnit.SECONDS));
                ch.pipeline().addLast(new HeartBeatHandler());
            }
        };
    }

    private void listen() {
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess()) {
                this.channel = channelFuture.channel();
                log.info("诺凡代理器开启成功，服务器地址[{}]", port);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            shutdown();
        }
    }

    private void shutdown() {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }

        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    private boolean isLinux() {

        String osName = System.getProperty("os.name");
        return osName != null && osName.toLowerCase().contains("linux");
    }
}

