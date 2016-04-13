package com.saic.ebiz.vbox.stream.bean;

import com.saic.ebiz.vbox.conf.Descriptor;

/**
 * 基站信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class StationInfo extends MessageEntity{

    // 基站码
    @Descriptor(value="基站码")
    private String baseStation;
    // 小区码
    @Descriptor(value="小区码")
    private String blockCode;
    // 信号强度
    @Descriptor(value="信号强度")
    private Integer signal;

    public StationInfo(String baseStation, String blockCode, Integer signal) {
        this.baseStation=baseStation;
        this.blockCode=blockCode;
        this.signal=signal;
    }

    public String getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(String baseStation) {
        this.baseStation = baseStation;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public Integer getSignal() {
        return signal;
    }

    public void setSignal(Integer signal) {
        this.signal = signal;
    }

}
