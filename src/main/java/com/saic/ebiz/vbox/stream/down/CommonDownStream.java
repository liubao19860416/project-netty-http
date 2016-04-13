package com.saic.ebiz.vbox.stream.down;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.DownStream;

/**
 * 下行数据包的通用响应类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月18日
 * 
 */
public class CommonDownStream extends DownStream {

    private static final long serialVersionUID = 6759834712964293632L;
    @Descriptor(value="应答流水号/设备消息的流水号")
    private String responseNumber;
    @Descriptor(value="应答ID/设备消息命令ID")
    private String responseId;
    @Descriptor(value="结果( 0:成功/确认;1:失败;2:消息有误;3:不支持;4:报警处理确认)")
    private String responseResult;
    
    public CommonDownStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String commandName,
            String responseNumber, String responseId, String responseResult) {
        this(commandType, msgProperty, sn, msgNumber, msgBody, responseNumber,
                responseId, responseResult);
        super.setCommandName(commandName);
    }

    public CommonDownStream(String commandType, String msgProperty, String sn,
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
