package com.saic.ebiz.vbox.stream.up;

import com.saic.ebiz.vbox.stream.base.UpStream;

/**
 * 心跳包-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月18日
 * 
 */
public class HeatbeatUpStream extends UpStream {
    
    private static final long serialVersionUID = -8501231328954898765L;

    public HeatbeatUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
    }

}
