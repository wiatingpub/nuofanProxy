package com.nuofankj.nuofanProxy.service;

import com.nuofankj.socket.manager.ChannelSession;

/**
 * @author xifanxiaxue
 * @date 2020/7/1 8:24
 * @desc
 */
public interface AuthService {

    /**
     * 注册
     *
     * @param channelSession
     * @param remoteOpenPort
     * @param password
     */
    void register(ChannelSession channelSession, int remoteOpenPort, String password);
}
