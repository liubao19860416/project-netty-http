package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;
import java.util.List;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.Pid;

/**
 * 一键检测应答-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月29日
 * 
 */
public class OneKeyDetectionUpStream extends UpStream {

    private static final long serialVersionUID = -8501231328954898765L;

    // 时间戳
    @Descriptor(value = "时间戳")
    private Timestamp timeStamps;
    // 行程ID/行程流水号
    @Descriptor(value = "行程ID/行程流水号")
    private String tripCount;
    // 设备拔插次数
    @Descriptor(value = "设备拔插次数")
    private Long uuid;

    // 一键检测数据
    // 主要系统故障检测
    @Descriptor(value = "主要系统故障检测")
    private String mainSystemDetectionStr;
    private byte[] mainSystemDetectionBytes;
    // 主要PID数据
    @Descriptor(value = "主要PID数据")
    private List<Pid> pidList;
    private byte[] pidListBytes;
    // 主要EPID数据
    @Descriptor(value = "主要EPID数据")
    private List<Pid> epidList;
    private byte[] epidListBytes;

    public OneKeyDetectionUpStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, Timestamp timeStamps,
            String tripCount, Long uuid, String mainSystemDetectionStr,
            byte[] mainSystemDetectionBytes, List<Pid> pidList,
            byte[] pidListBytes, List<Pid> epidList, byte[] epidListBytes) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.timeStamps = timeStamps;
        this.tripCount = tripCount;
        this.uuid = uuid;
        this.mainSystemDetectionStr = mainSystemDetectionStr;
        this.mainSystemDetectionBytes = mainSystemDetectionBytes;
        this.pidList = pidList;
        this.pidListBytes = pidListBytes;
        this.epidList = epidList;
        this.epidListBytes = epidListBytes;
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

    public String getMainSystemDetectionStr() {
        return mainSystemDetectionStr;
    }

    public void setMainSystemDetectionStr(String mainSystemDetectionStr) {
        this.mainSystemDetectionStr = mainSystemDetectionStr;
    }

    public byte[] getMainSystemDetectionBytes() {
        return mainSystemDetectionBytes;
    }

    public void setMainSystemDetectionBytes(byte[] mainSystemDetectionBytes) {
        this.mainSystemDetectionBytes = mainSystemDetectionBytes;
    }

    public List<Pid> getPidList() {
        return pidList;
    }

    public void setPidList(List<Pid> pidList) {
        this.pidList = pidList;
    }

    public byte[] getPidListBytes() {
        return pidListBytes;
    }

    public void setPidListBytes(byte[] pidListBytes) {
        this.pidListBytes = pidListBytes;
    }

    public List<Pid> getEpidList() {
        return epidList;
    }

    public void setEpidList(List<Pid> epidList) {
        this.epidList = epidList;
    }

    public byte[] getEpidListBytes() {
        return epidListBytes;
    }

    public void setEpidListBytes(byte[] epidListBytes) {
        this.epidListBytes = epidListBytes;
    }

}
