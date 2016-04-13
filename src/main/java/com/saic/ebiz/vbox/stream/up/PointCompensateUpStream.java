package com.saic.ebiz.vbox.stream.up;

import java.sql.Timestamp;

import com.saic.ebiz.vbox.stream.base.BasicUpStream;
import com.saic.ebiz.vbox.stream.bean.GPS;

/**
 * 拐点补偿数据包-上行数据包
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class PointCompensateUpStream extends BasicUpStream {
    private static final long serialVersionUID = -5021241447573390890L;

    public PointCompensateUpStream(String commandType, String msgProperty,
            String sn, String msgNumber, String msgBody, String checkCode,
            byte[] dataStream, String commandFull, Timestamp timeStamps,
            String tripCount, Long uuid, String statusFlag, GPS gps) {
        super(commandType, msgProperty, sn, msgNumber, msgBody, checkCode, dataStream,
                commandFull, timeStamps, tripCount, uuid, statusFlag, gps);
    }

}
