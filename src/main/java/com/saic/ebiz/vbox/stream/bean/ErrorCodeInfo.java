package com.saic.ebiz.vbox.stream.bean;

import com.saic.ebiz.vbox.conf.Descriptor;

/**
 * ErrorCode详情实体信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月28日
 * 
 */
public class ErrorCodeInfo extends MessageEntity {

    // 故障码类型U1
    @Descriptor(value="故障码类型")
    private String name;
    // 故障码原始值U2
    @Descriptor(value="故障码原始值")
    private String originValue;
    // 故障码状态U1
    @Descriptor(value="故障码状态")
    private String status;
    
    public ErrorCodeInfo(String name, String originValue,String status) {
        super();
        this.name = name;
        this.originValue = originValue;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginValue() {
        return originValue;
    }

    public void setOriginValue(String originValue) {
        this.originValue = originValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
