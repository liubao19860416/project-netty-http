package com.saic.ebiz.vbox.stream.base;


/**
 * 下行数据抽象类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
public abstract class DownStream extends AbstractStream {

    private static final long serialVersionUID = -4312504513265389206L;
    
    public DownStream() {
        super();
    }

    public DownStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, "");
    }

}
