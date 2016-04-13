package com.saic.ebiz.vbox.stream.down;

import com.saic.ebiz.vbox.stream.base.DownStream;

/**
 * 下行数据实体(数据体数据为空)-清除报警消息/查询终端属性/位置信息查询
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年2月19日
 * 
 */
public class EmptyMsgBodyDownStream extends DownStream {

    private static final long serialVersionUID = 6759834712964293632L;
    
    public EmptyMsgBodyDownStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String commandName) {
        this(commandType, msgProperty, sn, msgNumber, msgBody);
        super.setCommandName(commandName);
    }

    public EmptyMsgBodyDownStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody) {
        super(commandType, msgProperty, sn, msgNumber, msgBody);
    }

}
