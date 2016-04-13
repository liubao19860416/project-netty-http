package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.base.BasicUpStream;
import com.saic.ebiz.vbox.stream.bean.GPS;

/**
 * 报警数据包-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class AlarmUpStream extends BasicUpStream {

    private static final long serialVersionUID = -5021241447573390890L;

    // 车型ID(obd厂家车型)
    @Descriptor(value="车型ID(obd厂家车型)")
    private String modelId;
    // 报警状态
    @Descriptor(value="报警状态")
    private String alarmStatus;
    
    public AlarmUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, Timestamp timeStamps,
            String tripCount, Long uuid, String statusFlag, GPS gps,
            String modelId, String alarmStatus) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull, timeStamps, tripCount, uuid,
                statusFlag, gps);
        this.modelId = modelId;
        this.alarmStatus = alarmStatus;
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

}
