package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.BasicUpStream;
import com.saic.ebiz.vbox.stream.bean.GPS;

/**
 * 基本信息包-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class BasicInfoUpStream extends BasicUpStream {

    private static final long serialVersionUID = -5021241447573390890L;

    // 设备版本号/软硬件版本号
    @Descriptor(value="设备版本号/软硬件版本号")
    private String deviceVersion;
    // IMEI号
    @Descriptor(value="IMEI号")
    private String imeiSeriesNum;
    // IMSI号
    @Descriptor(value="IMSI号")
    private String imsiSeriesNum;
    // ICCID号
    @Descriptor(value="ICCID号")
    private String iccidSeriesNum;
    // 车型ID(obd厂家车型)
    @Descriptor(value="车型ID(obd厂家车型)")
    private String modelId;
    // 车辆VIN码
    @Descriptor(value="车辆VIN码")
    private String vinCode;
    // 码表里程
    @Descriptor(value="码表里程")
    private Long mileage;

    public BasicInfoUpStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, Timestamp timeStamps,
            String tripCount, Long uuid, String statusFlag, GPS gps,
            String deviceVersion, String imeiSeriesNum, String imsiSeriesNum,
            String iccidSeriesNum, String modelId, String vinCode,
            Long mileage) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull, timeStamps, tripCount, uuid,
                statusFlag, gps);
        this.deviceVersion = deviceVersion;
        this.imeiSeriesNum = imeiSeriesNum;
        this.imsiSeriesNum = imsiSeriesNum;
        this.iccidSeriesNum = iccidSeriesNum;
        this.modelId = modelId;
        this.vinCode = vinCode;
        this.mileage = mileage;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getImeiSeriesNum() {
        return imeiSeriesNum;
    }

    public void setImeiSeriesNum(String imeiSeriesNum) {
        this.imeiSeriesNum = imeiSeriesNum;
    }

    public String getImsiSeriesNum() {
        return imsiSeriesNum;
    }

    public void setImsiSeriesNum(String imsiSeriesNum) {
        this.imsiSeriesNum = imsiSeriesNum;
    }

    public String getIccidSeriesNum() {
        return iccidSeriesNum;
    }

    public void setIccidSeriesNum(String iccidSeriesNum) {
        this.iccidSeriesNum = iccidSeriesNum;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

}
