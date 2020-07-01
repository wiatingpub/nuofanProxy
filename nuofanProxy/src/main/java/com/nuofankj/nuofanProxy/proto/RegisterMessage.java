package com.nuofankj.nuofanProxy.proto;

import com.nuofankj.socket.proto.AbstractMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xifanxiaxue
 * @date 2020/7/1 8:19
 * @desc 注册协议
 */
@Setter
@Getter
public class RegisterMessage extends AbstractMessage {

    /**
     * 远程服务器的公网端口
     */
    private int remoteOpenPort;

    /**
     * 连接密码
     */
    private String password;

    public RegisterMessage() {
        super(2);
    }
}
