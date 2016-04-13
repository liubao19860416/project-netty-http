package com.saic.ebiz.vbox.stream.base;

import java.sql.Timestamp;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.bean.GPS;

/**
 * 上行数据抽象实体(包含基本的时间戳等数据信息实体)
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public abstract class BasicUpStream extends UpStream {

    private static final long serialVersionUID = 5791426317094542050L;

    // 时间戳
    @Descriptor(value="时间戳")
    private Timestamp timeStamps;
    // 行程ID/行程流水号
    @Descriptor(value="行程ID/行程流水号")
    private String tripCount;
    // 设备拔插次数
    @Descriptor(value="设备拔插次数")
    private Long uuid;
    // 状态标识
    @Descriptor(value="状态标识")
    private String statusFlag;
    // 位置信息
    @Descriptor(value="位置信息")
    private GPS gps;

    public BasicUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, 
            Timestamp timeStamps,String tripCount, Long uuid, String statusFlag, GPS gps) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull);
        this.timeStamps=timeStamps;
        this.tripCount=tripCount;
        this.uuid=uuid;
        this.statusFlag=statusFlag;
        this.gps=gps;
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
}
