package com.saic.ebiz.vbox.stream.up;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.UpStream;

/**
 * 设备通用应答-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月18日
 * 
 */
public class CommonUpStream extends UpStream {

    private static final long serialVersionUID = -8501231328954898765L;

    //应答流水号,对应设备消息的流水号
    @Descriptor(value="应答流水号,对应设备消息的流水号")
    private String responseNumber;
    //应答ID,对应设备消息的命令ID
    @Descriptor(value="应答ID,对应设备消息的命令ID")
    private String responseId;
    //应答结果
    @Descriptor(value="应答结果")
    private String responseResult;

    public CommonUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, String responseId,
            String responseNumber, String responseResult) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.responseId = responseId;
        this.responseNumber = responseNumber;
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
