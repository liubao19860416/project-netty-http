package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;

import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.stream.bean.GPS;

/**
 * 位置信息查询应答（上行）-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月26日
 * 
 */
public class QueryLocationUpStream extends TimingUpStream {
    
    private static final long serialVersionUID = -8501231328954898766L;
    
    // 应答指令流水号
    @Descriptor(value="应答指令流水号")
    private String responseNumber;
    
    public QueryLocationUpStream(String commandType, String msgProperty, String sn,
            String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull,String responseNumber,Timestamp timeStamps,
            String tripCount, Long uuid, String statusFlag, GPS gps,
            String countyCode, String telecomCode, int stationNumber,
            byte[] stationInfoListByte, String alarmStatus,
            String modelId, int pidCount, byte[] pidListByte) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull, timeStamps, tripCount, uuid,
                statusFlag, gps, countyCode, telecomCode, stationNumber,
                stationInfoListByte, alarmStatus, modelId, pidCount,
                pidListByte);
        this.responseNumber=responseNumber;
    }

}
