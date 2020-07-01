package com.nuofankj.nuofanProxy.facade;

import com.nuofankj.nuofanProxy.proto.RegisterMessage;
import com.nuofankj.nuofanProxy.service.AuthService;
import com.nuofankj.socket.dispatcher.anno.Api;
import com.nuofankj.socket.manager.ChannelSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xifanxiaxue
 * @date 2020/7/1 8:19
 * @desc 权限相关
 */
@Component
public class AuthFacade {

    @Autowired
    private AuthService authService;

    @Api
    public void register(ChannelSession channelSession, RegisterMessage registerMessage) {
        authService.register(channelSession, registerMessage.getRemoteOpenPort(), registerMessage.getPassword());
    }
}
