package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;
import java.util.List;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.ErrorCodeInfo;

/**
 * 故障码信息-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class ErrorCodeUpStream extends UpStream {

    private static final long serialVersionUID = -5021241447573390890L;

    // 时间戳
    @Descriptor(value="时间戳")
    private Timestamp timeStamps;
    // 行程ID/行程流水号
    @Descriptor(value="行程ID/行程流水号")
    private String tripCount;
    // 设备拔插次数
    @Descriptor(value="设备拔插次数")
    private Long uuid;
    // 数据状态标识
    @Descriptor(value="数据状态标识")
    private String statusFlag;
    // 故障码个数
    @Descriptor(value="故障码个数")
    private Integer errorCodeNumber;
    // 故障码信息列表
    @Descriptor(value="故障码信息Byte列表")
    private byte[] errorCodeInfoListByte;
    @Descriptor(value="故障码信息List列表")
    private List<ErrorCodeInfo> errorCodeInfoList;
    
    public ErrorCodeUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, Timestamp timeStamps,
            String tripCount, Long uuid, String statusFlag,
            Integer errorCodeNumber, byte[] errorCodeInfoListByte) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.timeStamps = timeStamps;
        this.tripCount = tripCount;
        this.uuid = uuid;
        this.statusFlag = statusFlag;
        this.errorCodeNumber = errorCodeNumber;
        this.errorCodeInfoListByte = errorCodeInfoListByte;
    }
    
    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Integer getErrorCodeNumber() {
        return errorCodeNumber;
    }

    public void setErrorCodeNumber(Integer errorCodeNumber) {
        this.errorCodeNumber = errorCodeNumber;
    }

    public byte[] getErrorCodeInfoListByte() {
        return errorCodeInfoListByte;
    }

    public void setErrorCodeInfoListByte(byte[] errorCodeInfoListByte) {
        this.errorCodeInfoListByte = errorCodeInfoListByte;
    }

    public List<ErrorCodeInfo> getErrorCodeInfoList() {
        return errorCodeInfoList;
    }

    public void setErrorCodeInfoList(List<ErrorCodeInfo> errorCodeInfoList) {
        this.errorCodeInfoList = errorCodeInfoList;
    }

    public Timestamp getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(Timestamp timeStamps) {
        this.timeStamps = timeStamps;
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
    
}
