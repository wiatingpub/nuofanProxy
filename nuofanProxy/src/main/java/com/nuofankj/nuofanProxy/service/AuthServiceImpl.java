package com.nuofankj.nuofanProxy.service;

import com.nuofankj.socket.manager.ChannelSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xifanxiaxue
 * @date 2020/7/1 8:24
 * @desc 权限相关
 */
@Slf4j
@Component
public class AuthServiceImpl implements AuthService {
    @Override
    public void register(ChannelSession channelSession, int remoteOpenPort, String password) {
        log.info("有连接注册，端口：{}，密码：{}", remoteOpenPort, password);
    }
}
