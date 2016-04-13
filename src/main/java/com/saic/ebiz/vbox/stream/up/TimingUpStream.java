package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;
import java.util.List;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.BasicUpStream;
import com.saic.ebiz.vbox.stream.bean.GPS;
import com.saic.ebiz.vbox.stream.bean.Pid;
import com.saic.ebiz.vbox.stream.bean.StationInfo;

/**
 * 定时数据包-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
public class TimingUpStream extends BasicUpStream {

    private static final long serialVersionUID = -5021241447573390890L;

    // 国家代码
    @Descriptor(value="国家代码")
    private String countyCode;
    // 网络运营商代码
    @Descriptor(value="网络运营商代码")
    private String telecomCode;
    // 基站个数
    @Descriptor(value="基站个数")
    private Integer stationNumber;
    // 基站信息列表
    @Descriptor(value="基站信息列表")
    private List<StationInfo> stationInfoList;
    private byte[] stationInfoListByte;

    // 报警状态
    @Descriptor(value="报警状态")
    private String alarmStatus;
    // 车型ID(obd厂家车型)
    @Descriptor(value="车型ID(obd厂家车型)")
    private String modelId;
    // obd数据流PID个数
    @Descriptor(value="obd数据流PID个数")
    private Integer pidCount;
    // pid数据流
    @Descriptor(value="pid数据流")
    private List<Pid> pidList;
    private byte[] pidListByte;
    
    public TimingUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, Timestamp timeStamps,
            String tripCount, Long uuid, String statusFlag, GPS gps,
            String countyCode, String telecomCode, int stationNumber,
            byte[] stationInfoListByte, String alarmStatus,
            String modelId, int pidCount, byte[] pidListByte) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull, timeStamps, tripCount, uuid,
                statusFlag, gps);
        this.countyCode = countyCode;
        this.telecomCode = telecomCode;
        this.stationNumber = stationNumber;
        this.stationInfoListByte = stationInfoListByte;
        this.alarmStatus = alarmStatus;
        this.modelId = modelId;
        this.pidCount = pidCount;
        this.pidListByte = pidListByte;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getTelecomCode() {
        return telecomCode;
    }

    public void setTelecomCode(String telecomCode) {
        this.telecomCode = telecomCode;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Integer getPidCount() {
        return pidCount;
    }

    public void setPidCount(Integer pidCount) {
        this.pidCount = pidCount;
    }

    public Integer getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(Integer stationNumber) {
        this.stationNumber = stationNumber;
    }

    public List<StationInfo> getStationInfoList() {
        return stationInfoList;
    }

    public void setStationInfoList(List<StationInfo> stationInfoList) {
        this.stationInfoList = stationInfoList;
    }

    public byte[] getStationInfoListByte() {
        return stationInfoListByte;
    }

    public void setStationInfoListByte(byte[] stationInfoListByte) {
        this.stationInfoListByte = stationInfoListByte;
    }

    public List<Pid> getPidList() {
        return pidList;
    }

    public void setPidList(List<Pid> pidList) {
        this.pidList = pidList;
    }

    public byte[] getPidListByte() {
        return pidListByte;
    }

    public void setPidListByte(byte[] pidListByte) {
        this.pidListByte = pidListByte;
    }

}
