package com.saic.ebiz.vbox.stream.up;

import com.saic.ebiz.vbox.stream.base.UpStream;

/**
 * 登录报文-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月18日
 * 
 */
public class LoginUpStream extends UpStream {
    private static final long serialVersionUID = -8501231328954898765L;

    public LoginUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
    }
    
}
