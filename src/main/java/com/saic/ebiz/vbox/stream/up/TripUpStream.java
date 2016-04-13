package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;

import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.GPS;

/**
 * 行程数据包-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class TripUpStream extends UpStream {

    private static final long serialVersionUID = -5021241447573390890L;

    // 开始时间
    private Timestamp beginDatetime;
    // 结束时间
    private Timestamp endDatetime;
    // 行程ID/行程流水号
    private String tripCount;
    // 设备拔插次数
    private Long uuid;
    // 状态标识
    private String statusFlag;
    // 位置信息
    private GPS gps;

    // 经济性数据
    // 行程里程
    private Float tripMileage;
    // 行程油耗
    private Float tripFuel;
    // 行驶时间
    private Float tripTime;
    // 怠速时间
    private Float idleTime;

    // 危险驾驶行为统计
    // 急加速次数
    private Integer hurrySpeedup;
    // 急减速次数
    private Integer hurryBrake;
    // 急转弯次数
    private Integer hurryChange;
    // 超速次数
    private Integer overSpeed;
    // 超速时长
    private Long overSpeedTime;
    // 刹车次数
    private Integer brakeTime;

    // 数据统计
    // 最高速度
    private Float maxSpeed;
    // 平均速度
    private Float avgSpeed;
    // 最高转速
    private Float maxRotateSpeed;
    // 平均转速
    private Float avgRotateSpeed;
    // 最大油门开度
    private Float maxGasOpen;
    // 平均油门开度
    private Float avgGasOpen;

    // 速度(S)里程统计
    private byte[] speedMileageStatistics;
    // 转速(R)里程统计
    private byte[] rotateSpeedMileageStatistics;
    // 油门(O)里程统计
    private byte[] gasMileageStatistics;
    
    public TripUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull,
            Timestamp beginDatetime, Timestamp endDatetime, String tripCount,
            Long uuid, String statusFlag, GPS gps, Float tripMileage,
            Float tripFuel, Float tripTime, Float idleTime,
            Integer hurrySpeedup, Integer hurryBrake, Integer hurryChange,
            Integer overSpeed, Long overSpeedTime, Integer brakeTime,
            Float maxSpeed, Float avgSpeed, Float maxRotateSpeed,
            Float avgRotateSpeed, Float maxGasOpen, Float avgGasOpen,
            byte[] speedMileageStatistics, byte[] rotateSpeedMileageStatistics,
            byte[] gasMileageStatistics) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.beginDatetime = beginDatetime;
        this.endDatetime = endDatetime;
        this.tripCount = tripCount;
        this.uuid = uuid;
        this.statusFlag = statusFlag;
        this.gps = gps;
        this.tripMileage = tripMileage;
        this.tripFuel = tripFuel;
        this.tripTime = tripTime;
        this.idleTime = idleTime;
        this.hurrySpeedup = hurrySpeedup;
        this.hurryBrake = hurryBrake;
        this.hurryChange = hurryChange;
        this.overSpeed = overSpeed;
        this.overSpeedTime = overSpeedTime;
        this.brakeTime = brakeTime;
        this.maxSpeed = maxSpeed;
        this.avgSpeed = avgSpeed;
        this.maxRotateSpeed = maxRotateSpeed;
        this.avgRotateSpeed = avgRotateSpeed;
        this.maxGasOpen = maxGasOpen;
        this.avgGasOpen = avgGasOpen;
        this.speedMileageStatistics = speedMileageStatistics;
        this.rotateSpeedMileageStatistics = rotateSpeedMileageStatistics;
        this.gasMileageStatistics = gasMileageStatistics;
    }

    public Timestamp getBeginDatetime() {
        return beginDatetime;
    }

    public void setBeginDatetime(Timestamp beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public Timestamp getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Timestamp endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getTripCount() {
        return tripCount;
    }

    public void setTripCount(String tripCount) {
        this.tripCount = tripCount;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    public Float getTripMileage() {
        return tripMileage;
    }

    public void setTripMileage(Float tripMileage) {
        this.tripMileage = tripMileage;
    }

    public Float getTripFuel() {
        return tripFuel;
    }

    public void setTripFuel(Float tripFuel) {
        this.tripFuel = tripFuel;
    }

    public Float getTripTime() {
        return tripTime;
    }

    public void setTripTime(Float tripTime) {
        this.tripTime = tripTime;
    }

    public Float getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(Float idleTime) {
        this.idleTime = idleTime;
    }

    public Integer getHurrySpeedup() {
        return hurrySpeedup;
    }

    public void setHurrySpeedup(Integer hurrySpeedup) {
        this.hurrySpeedup = hurrySpeedup;
    }

    public Integer getHurryBrake() {
        return hurryBrake;
    }

    public void setHurryBrake(Integer hurryBrake) {
        this.hurryBrake = hurryBrake;
    }

    public Integer getHurryChange() {
        return hurryChange;
    }

    public void setHurryChange(Integer hurryChange) {
        this.hurryChange = hurryChange;
    }

    public Integer getOverSpeed() {
        return overSpeed;
    }

    public void setOverSpeed(Integer overSpeed) {
        this.overSpeed = overSpeed;
    }

    public Long getOverSpeedTime() {
        return overSpeedTime;
    }

    public void setOverSpeedTime(Long overSpeedTime) {
        this.overSpeedTime = overSpeedTime;
    }

    public Integer getBrakeTime() {
        return brakeTime;
    }

    public void setBrakeTime(Integer brakeTime) {
        this.brakeTime = brakeTime;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Float getMaxRotateSpeed() {
        return maxRotateSpeed;
    }

    public void setMaxRotateSpeed(Float maxRotateSpeed) {
        this.maxRotateSpeed = maxRotateSpeed;
    }

    public Float getAvgRotateSpeed() {
        return avgRotateSpeed;
    }

    public void setAvgRotateSpeed(Float avgRotateSpeed) {
        this.avgRotateSpeed = avgRotateSpeed;
    }

    public Float getMaxGasOpen() {
        return maxGasOpen;
    }

    public void setMaxGasOpen(Float maxGasOpen) {
        this.maxGasOpen = maxGasOpen;
    }

    public Float getAvgGasOpen() {
        return avgGasOpen;
    }

    public void setAvgGasOpen(Float avgGasOpen) {
        this.avgGasOpen = avgGasOpen;
    }

    public byte[] getSpeedMileageStatistics() {
        return speedMileageStatistics;
    }

    public void setSpeedMileageStatistics(byte[] speedMileageStatistics) {
        this.speedMileageStatistics = speedMileageStatistics;
    }

    public byte[] getRotateSpeedMileageStatistics() {
        return rotateSpeedMileageStatistics;
    }

    public void setRotateSpeedMileageStatistics(
            byte[] rotateSpeedMileageStatistics) {
        this.rotateSpeedMileageStatistics = rotateSpeedMileageStatistics;
    }

    public byte[] getGasMileageStatistics() {
        return gasMileageStatistics;
    }

    public void setGasMileageStatistics(byte[] gasMileageStatistics) {
        this.gasMileageStatistics = gasMileageStatistics;
    }

}
