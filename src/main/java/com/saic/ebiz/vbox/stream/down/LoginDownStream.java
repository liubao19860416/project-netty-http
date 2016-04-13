package com.saic.ebiz.vbox.stream.down;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.DownStream;

/**
 * 下行数据包-登录应答
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月27日
 * 
 */
public class LoginDownStream extends DownStream {

    private static final long serialVersionUID = 6759834712964293632L;
    @Descriptor(value="应答流水号/设备消息的流水号")
    private String responseNumber;
    @Descriptor(value="应答ID/设备消息命令ID")
    private String responseId;
    @Descriptor(value="结果(GMT格式时间戳)")
    private String responseResult;
    
    public LoginDownStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String commandName,
            String responseNumber, String responseId, String responseResult) {
        this(commandType, msgProperty, sn, msgNumber, msgBody, responseNumber,
                responseId, responseResult);
        super.setCommandName(commandName);
    }

    public LoginDownStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String responseNumber,
            String responseId, String responseResult) {
        super(commandType, msgProperty, sn, msgNumber, msgBody);
        this.responseNumber = responseNumber;
        this.responseId = responseId;
        this.responseResult = responseResult;
    }

    public String getResponseNumber() {
        return responseNumber;
    }

    public void setResponseNumber(String responseNumber) {
        this.responseNumber = responseNumber;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

}
