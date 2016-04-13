package com.saic.ebiz.vbox.stream.bean;

import com.saic.ebiz.vbox.conf.Descriptor;

/**
 * GPS定位信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月18日
 * 
 */
public class GPS extends MessageEntity{

    // 经度
    @Descriptor(value="经度")
    private Float longitude;
    // 纬度
    @Descriptor(value="纬度")
    private Float latitude;
    // 当前GPS高度
    @Descriptor(value="当前GPS高度")
    private Float height;
    // 当前GPS速度
    @Descriptor(value="当前GPS速度")
    private Float speed;
    // 方向
    @Descriptor(value="方向")
    private Float direction;
    // 卫星数
    @Descriptor(value="卫星数")
    private Integer satelliteNumber;
    // 位置精度强弱度
    @Descriptor(value="位置精度强弱度")
    private Float pdop;

    public GPS(Float longitude, Float latitude, Float height,
            Float speed, Float direction, Integer satelliteNumber,
            Float pdop) {
        this.longitude=longitude;
        this.latitude=latitude;
        this.height=height;
        this.speed=speed;
        this.direction=direction;
        this.satelliteNumber=satelliteNumber;
        this.pdop=pdop;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getDirection() {
        return direction;
    }

    public void setDirection(Float direction) {
        this.direction = direction;
    }

    public Integer getSatelliteNumber() {
        return satelliteNumber;
    }

    public void setSatelliteNumber(Integer satelliteNumber) {
        this.satelliteNumber = satelliteNumber;
    }

    public Float getPdop() {
        return pdop;
    }

    public void setPdop(Float pdop) {
        this.pdop = pdop;
    }

}
