package com.saic.ebiz.vbox.stream.up;

import java.util.List;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.Parameter;

/**
 * 查询参数应答（上行）-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月18日
 * 
 */
public class QueryParametersUpStream extends UpStream {
    
    private static final long serialVersionUID = -8501231328954898766L;
    
    // 应答指令流水号
    @Descriptor(value="应答指令流水号")
    private String responseNumber;
    // 参数信息HEX字符串
    @Descriptor(value="参数信息HEX字符串")
    private String parameters;
    // 参数总数
    @Descriptor(value="参数总数")
    private Integer parameterCount;
    // 参数信息集合
    @Descriptor(value="参数信息集合")
    private List<Parameter> parameterList;

    public QueryParametersUpStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, String responseNumber,
            String parameters, Integer parameterCount,
            List<Parameter> parameterList) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.responseNumber = responseNumber;
        this.parameters = parameters;
        this.parameterCount = parameterCount;
        this.parameterList = parameterList;
    }

    public String getResponseNumber() {
        return responseNumber;
    }

    public void setResponseNumber(String responseNumber) {
        this.responseNumber = responseNumber;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Integer getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(Integer parameterCount) {
        this.parameterCount = parameterCount;
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }
    
}
