package com.saic.ebiz.vbox.stream.bean;

import java.sql.Timestamp;

import com.saic.ebiz.vbox.conf.Descriptor;

/**
 * 车身状态信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class CarStatus extends MessageEntity {

    // 第一组CAN数据的时间,以后每组间隔一秒。使用GMT时间.
    @Descriptor(value="CAN数据的GMT时间")
    private Timestamp timeStamps;
    // 灯状态
    @Descriptor(value="灯状态")
    private String lampStatus;
    // 指示灯状态
    @Descriptor(value="指示灯状态")
    private String indicatorLampStatus;
    // 门状态
    @Descriptor(value="门状态")
    private String doorStatus;
    // 档位状态
    @Descriptor(value="档位状态")
    private String gearShiftStatus;
    // 遥控器状态
    @Descriptor(value="遥控器状态")
    private String remoteControlStatus;
    // 窗户状态
    @Descriptor(value="窗户状态")
    private String windowStatus;
    
    public CarStatus(Timestamp timeStamps, String lampStatus,
            String indicatorLampStatus, String doorStatus,
            String gearShiftStatus, String remoteControlStatus,
            String windowStatus) {
        super();
        this.timeStamps = timeStamps;
        this.lampStatus = lampStatus;
        this.indicatorLampStatus = indicatorLampStatus;
        this.doorStatus = doorStatus;
        this.gearShiftStatus = gearShiftStatus;
        this.remoteControlStatus = remoteControlStatus;
        this.windowStatus = windowStatus;
    }

    public Timestamp getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(Timestamp timeStamps) {
        this.timeStamps = timeStamps;
    }

    public String getLampStatus() {
        return lampStatus;
    }

    public void setLampStatus(String lampStatus) {
        this.lampStatus = lampStatus;
    }

    public String getIndicatorLampStatus() {
        return indicatorLampStatus;
    }

    public void setIndicatorLampStatus(String indicatorLampStatus) {
        this.indicatorLampStatus = indicatorLampStatus;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getGearShiftStatus() {
        return gearShiftStatus;
    }

    public void setGearShiftStatus(String gearShiftStatus) {
        this.gearShiftStatus = gearShiftStatus;
    }

    public String getRemoteControlStatus() {
        return remoteControlStatus;
    }

    public void setRemoteControlStatus(String remoteControlStatus) {
        this.remoteControlStatus = remoteControlStatus;
    }

    public String getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(String windowStatus) {
        this.windowStatus = windowStatus;
    }

}
