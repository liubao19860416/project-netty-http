package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;
import java.util.List;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.Pid;

/**
 * CAN数据连续包-上行数据包 
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月18日
 * 
 */
public class CanDataUpStream extends UpStream {

    private static final long serialVersionUID = -8501231328954898765L;
    
    // 时间戳
    @Descriptor(value="时间戳")
    private Timestamp timeStamps;
    // 行程ID/行程流水号
    @Descriptor(value="行程ID/行程流水号")
    private String tripCount;
    // 设备拔插次数
    @Descriptor(value="设备拔插次数")
    private Long uuid;
    // 单组数据PID个数及补传标志
    @Descriptor(value="单组数据PID个数")
    private Integer pidCount;
    // 数据补传标志(0|1表示实时|盲区)
    @Descriptor(value="数据补传标志(0|1表示实时|盲区)")
    private String validMark;
    // 车型ID(obd厂家车型)
    @Descriptor(value="车型ID(obd厂家车型)")
    private String modelId;
    //CAN数据组
    @Descriptor(value="pid数据流")
    private List<List<Pid>> pidList;
    private byte[] pidListBytes;

    public CanDataUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, Timestamp timeStamps,
            String tripCount, Long uuid, Integer pidCount, String validMark,
            String modelId, byte[] pidListBytes) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.timeStamps = timeStamps;
        this.tripCount = tripCount;
        this.uuid = uuid;
        this.pidCount = pidCount;
        this.validMark = validMark;
        this.modelId = modelId;
        this.pidListBytes = pidListBytes;
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

    public Integer getPidCount() {
        return pidCount;
    }

    public void setPidCount(Integer pidCount) {
        this.pidCount = pidCount;
    }

    public String getValidMark() {
        return validMark;
    }

    public void setValidMark(String validMark) {
        this.validMark = validMark;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public List<List<Pid>> getPidList() {
        return pidList;
    }

    public void setPidList(List<List<Pid>> pidList) {
        this.pidList = pidList;
    }

    public byte[] getPidListBytes() {
        return pidListBytes;
    }

    public void setPidListBytes(byte[] pidListBytes) {
        this.pidListBytes = pidListBytes;
    }
    
}
