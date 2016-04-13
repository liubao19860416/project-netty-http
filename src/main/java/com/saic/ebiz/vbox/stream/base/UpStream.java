package com.saic.ebiz.vbox.stream.base;

import com.saic.ebiz.vbox.conf.StreamConstant;

/**
 * 上行数据抽象实体(不包含基本的时间戳等数据信息实体)
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public abstract class UpStream extends AbstractStream {

    private static final long serialVersionUID = 5791426317094542050L;

    public UpStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, String commandName) {
        this(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        super.setCommandName(commandName);
    }

    public UpStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull) {
        this(commandType, msgProperty, sn, msgNumber, msgBody, checkCode);
        super.setMarkBit(StreamConstant.MARK_BIT);
        super.setCommandFull(commandFull);
        super.setDataStream(dataStream);
    }

    public UpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode);
    }
    
}
