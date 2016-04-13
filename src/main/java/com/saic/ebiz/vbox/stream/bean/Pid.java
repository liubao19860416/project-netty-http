package com.saic.ebiz.vbox.stream.bean;

import com.saic.ebiz.vbox.conf.Descriptor;

/**
 * PID
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class Pid extends MessageEntity {

    // pid名称
    @Descriptor(value="pid名称")
    private String pid;
    // pid原始值{1,0,0,0,1};//0100000001===1,0,0,0,1
    @Descriptor(value="pid原始值")
    private String origin;
    //pid原始Byte值
    @Descriptor(value="pid原始Byte值")
    private byte[] originBytes;
    
    public Pid(String pid, String origin,byte[] originBytes) {
        super();
        this.pid = pid;
        this.origin = origin;
        this.originBytes = originBytes;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public byte[] getOriginBytes() {
        return originBytes;
    }

    public void setOriginBytes(byte[] originBytes) {
        this.originBytes = originBytes;
    }
    
}
