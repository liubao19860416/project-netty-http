package com.saic.ebiz.vbox.utils;

import io.netty.util.internal.StringUtil;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.saic.ebiz.vbox.conf.Descriptor;
import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.exception.ParseStreamException;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.CarStatus;
import com.saic.ebiz.vbox.stream.bean.ErrorCodeInfo;
import com.saic.ebiz.vbox.stream.bean.GPS;
import com.saic.ebiz.vbox.stream.bean.MessageEntity;
import com.saic.ebiz.vbox.stream.bean.Parameter;
import com.saic.ebiz.vbox.stream.bean.Pid;
import com.saic.ebiz.vbox.stream.bean.StationInfo;
import com.saic.ebiz.vbox.stream.up.AlarmUpStream;
import com.saic.ebiz.vbox.stream.up.BasicInfoUpStream;
import com.saic.ebiz.vbox.stream.up.CanDataUpStream;
import com.saic.ebiz.vbox.stream.up.CarStatusUpStream;
import com.saic.ebiz.vbox.stream.up.CommonUpStream;
import com.saic.ebiz.vbox.stream.up.ErrorCodeUpStream;
import com.saic.ebiz.vbox.stream.up.FullCarScanUpStream;
import com.saic.ebiz.vbox.stream.up.HeatbeatUpStream;
import com.saic.ebiz.vbox.stream.up.LoginUpStream;
import com.saic.ebiz.vbox.stream.up.OneKeyDetectionUpStream;
import com.saic.ebiz.vbox.stream.up.PointCompensateUpStream;
import com.saic.ebiz.vbox.stream.up.QueryLocationUpStream;
import com.saic.ebiz.vbox.stream.up.QueryParametersUpStream;
import com.saic.ebiz.vbox.stream.up.SleepInUpStream;
import com.saic.ebiz.vbox.stream.up.SleepWakeUpStream;
import com.saic.ebiz.vbox.stream.up.TimingUpStream;
import com.saic.ebiz.vbox.stream.up.TripUpStream;

/**
 * Lite+协议解析工具类:针对Stream类型数据封装的工具类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
public class StreamUtils {
    
    private static Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    /**
     * 处理解析命令类型:HEX格式的字符串
     */
	public static String parseCommandType(byte[] dataStream) {
//		return ByteUtil.bytes2HexString(dataStream,0,2);
//		return ByteUtil.bytes2HexString(dataStream,2);
		return StringUtil.toHexStringPadded(dataStream).substring(0, 4);
	}
	
	/**
	 * 解析消息属性字段
	 */
	public static String parseMsgProperty(String hexString) {
	    return hexString.substring(4, 8);
	}
	
	/**
	 * 解析设备ID字段
	 */
	public static String parseSN(String hexString) {
	    //return hexString.substring(8+1, 22);
	    return hexString.substring(8, 22);
	}
	
	/**
	 * 解析消息流水号字段
	 */
	public static String parseMsgNumber(String hexString) {
	    return hexString.substring(22, 26);
	}
	
	/**
	 * 解析消息体实体信息
	 */
	public static String parseMsgBody(String hexString) {
	    return hexString.substring(26, hexString.length());
	}
	
	/**
	 * 解析校验码字段
	 */
	public static String parseCheckCode(byte[] dataStream) {
	    return formatCheckCode(StringUtil.toHexStringPadded(
	            new byte[]{ByteUtil.calculateCheckCode(dataStream)}));
	}
	/**
	 * 格式化校验码字段
	 */
	public static String formatCheckCode(String checkCode) {
	    while(checkCode.length()<2){
	        checkCode="0"+checkCode;
	    }
	    return checkCode;
	}
	
	/**
	 * 解析时间戳:BCD码解析为GMT时间格式
	 */
	public static Timestamp parseGMTate(String hexGMTateStr) {
	    String year = hexGMTateStr.substring(0, 2);
	    String month = hexGMTateStr.substring(2, 4);
	    String day = hexGMTateStr.substring(4, 6);
	    String hour = hexGMTateStr.substring(6, 8);
	    String minute = hexGMTateStr.substring(8, 10);
	    String second = hexGMTateStr.substring(10, 12);
	    logger.info("解析得到的时间日期hour为:{}",ByteUtil.bcd2Str(ByteUtil.hexString2Bytes(hour)));
	    
	    StringBuilder sb=new StringBuilder();
        sb.append(year)
                .append(GMTDateUtil.GMT_DATE_SEPERATOR).append(month)
                .append(GMTDateUtil.GMT_DATE_SEPERATOR).append(day)
                .append(GMTDateUtil.GMT_DATE_SEPERATOR).append(hour)
                .append(GMTDateUtil.GMT_DATE_SEPERATOR).append(minute)
                .append(GMTDateUtil.GMT_DATE_SEPERATOR).append(second);
        logger.info("解析得到的时间日期yy-MM-dd-HH-mm-ss为:{}",sb.toString());
        Date parseGMT0Time = GMTDateUtil.parseGMT0Time(sb.toString());
	    return new Timestamp(parseGMT0Time.getTime());
	}
	
	/**
	 * 解析GPS
	 */
	public static GPS parseGPS(String gpsStr) {
        // 经度
	    Float longitude=ByteUtil.hexString2Long(gpsStr.substring(0, 8))*0.000001F;
        // 纬度
        Float latitude=ByteUtil.hexString2Long(gpsStr.substring(8, 16))*0.000001F;
        // 当前GPS高度
        Float height=ByteUtil.hexString2Long(gpsStr.substring(16, 20))*0.1F;
        // 当前GPS速度
        Float speed=ByteUtil.hexString2Long(gpsStr.substring(20, 24))*0.1F;
        // 方向
        Float direction=ByteUtil.hexString2Long(gpsStr.substring(24, 28))*0.1F;
        // 卫星数
        Integer satelliteNumber=ByteUtil.hexString2Int(gpsStr.substring(28, 30));
        // 位置精度强弱度
        Float pdop=ByteUtil.hexString2Long(gpsStr.substring(30, 34))*0.01F;
        logger.info("longitude:{}",MyStringUtil.formatNumber(longitude,6));
        logger.info("latitude:{}",MyStringUtil.formatNumber(latitude,6));
        logger.info("height:{}",MyStringUtil.formatNumber(height,1));
        logger.info("speed:{}",MyStringUtil.formatNumber(speed,1));
        logger.info("direction:{}",MyStringUtil.formatNumber(direction,1));
        logger.info("satelliteNumber:{}",satelliteNumber);
        logger.info("pdop:{}",pdop);
	    return new GPS(longitude,latitude,height,speed,direction,satelliteNumber,pdop);
	}
	
	/**
	 * 解析StationInfo集合
	 */
	public static List<StationInfo> parseStationInfoList(int stationNumber, String stationInfoListStr) {
	    logger.info("当前获得的StationInfo集合长度为:{}",stationNumber);
	    List<StationInfo> resultList=new ArrayList<StationInfo>(stationNumber); 
	    for (int i = 0; i < stationNumber; i++) {
	        int startIndex=i*5*2;
	        // 基站码
	        String baseStation=stationInfoListStr.substring(startIndex, startIndex+4);
	        // 小区码
	        String blockCode=stationInfoListStr.substring(startIndex+4, startIndex+8);
	        // 信号强度
	        Integer signal=ByteUtil.hexString2Int(stationInfoListStr.substring(startIndex+8, startIndex+10));
            resultList.add(new StationInfo(baseStation,blockCode,signal));
        }
	    return resultList;
	}
	
	/**
	 * 解析Pid集合:For TimingUpStream,QueryLocationUpStream
	 * @param pidCount pid个数
	 */
	public static List<Pid> parsePidList(int pidCount, String pidListStr) {
	    logger.info("parsePidList当前的pidList子集合长度为:{}",pidCount);
	    List<Pid> resultList=new ArrayList<Pid>(pidCount); 
        for (int i = 0; i < pidCount; i++) {
            int startIndex=i*5*2;
            // pid名称
            String pid=pidListStr.substring(startIndex, startIndex+2);
            // 原始值
            String origin=pidListStr.substring(startIndex+2, startIndex+10);
            //pid原始Byte值
            byte[] originBytes=ByteUtil.hexString2Bytes(origin);
            resultList.add(new Pid(pid,origin,originBytes));
        }
        return resultList; 
	}
	
	/**
	 * 解析Pid集合:For CanDataUpStream
	 */
	public static List<List<Pid>> parsePidListForCanDataUpStream(int pidCount, String pidListStr) {
	    //获取组数
	    int groupLnegth= 5*2*pidCount;
	    int pidCountNew= pidListStr.length()/groupLnegth;
	    List<List<Pid>> resultList=new ArrayList<List<Pid>>(pidCountNew); 
	    //组
	    logger.info("parsePidListForCanDataUpStream单组数据PID个数为:{}",pidCount);
	    logger.info("parsePidListForCanDataUpStream当前的pidList组数为:{}",pidCountNew);
	    for (int i = 0; i < pidCountNew; i++) {
	        int startIndex=i*groupLnegth;
	        // 一组pid字符串
	        String subPidListStr=pidListStr.substring(startIndex, startIndex+groupLnegth);
	        //获取每组里面的数据
	        List<Pid> paramList=parsePidList(pidCount, subPidListStr);
	        resultList.add(paramList);
	    }
	    
	    return resultList; 
	}
	
	/**
	 * 解析ErrorCode集合 
	 */
	public static List<ErrorCodeInfo> parseErrorCodeList(int errorCodeCount, String errorCodeInfoListStr) {
	    logger.info("parseErrorCodeList错误码个数为:{}",errorCodeCount);
	    List<ErrorCodeInfo> resultList=new ArrayList<ErrorCodeInfo>(errorCodeCount); 
	    for (int i = 0; i < errorCodeCount; i++) {
	        int startIndex=i*4*2;
	        // 故障码类型U1
	        String name=errorCodeInfoListStr.substring(startIndex, startIndex+2);
	        // 故障码原始值U2
	        String originValue=errorCodeInfoListStr.substring(startIndex+2, startIndex+6);
	        // 故障码状态U1
	        String status=errorCodeInfoListStr.substring(startIndex+6, startIndex+8);
	        resultList.add(new ErrorCodeInfo(name, originValue, status));
	    }
	    return resultList; 
	}
	
	/**
	 * 解析Parameter集合 
	 * TODO
	 */
	public static List<Parameter> parseParameterList(int parameterCount, String parameterListStr) {
	    logger.info("parseParameterList解析Parameter集合 个数为:{}",parameterCount);
	    List<Parameter> resultList=new ArrayList<Parameter>(parameterCount); 
	    int resultInterval=parameterListStr.length()/parameterCount-3*2;
	    for (int i = 0; i < parameterCount; i++) {
	        int startIndex=i*4*2;
	        // 参数ID 2
	        String id=parameterListStr.substring(startIndex, startIndex+4);
	        // 参数长度 1
	        Integer length=ByteUtil.hexString2Int(parameterListStr.substring(startIndex+4, startIndex+6));
	        // 参数值
	        String value=parameterListStr.substring(startIndex+6, startIndex+6+resultInterval);
	        byte[] idBytes=ByteUtil.hexString2Bytes(id);
            byte[] valueBytes=ByteUtil.hexString2Bytes(value);
            resultList.add(new Parameter(id, idBytes, length, value, valueBytes));
	    }
	    return null; 
	}
	
	/**
	 * 解析CarStatus集合:1,2,6+6+2=16字节
	 */
	public static List<CarStatus> parseCarStatus(String carStatusStr) {
	    int length = carStatusStr.length();
	    int carStatusNum = length/(14*2);
	    logger.info("parseCarStatus计算得到的CarStatus个数为:{}",carStatusNum);
	    List<CarStatus> resultList=new ArrayList<CarStatus>(carStatusNum); 
	    for (int i = 0; i < carStatusNum; i++) {
	        int startIndex=i*14*2;
	        //时间
	        String timeStampStr=carStatusStr.substring(startIndex, startIndex+12);
	        Timestamp timeStamps=parseGMTate(timeStampStr);
	        // 灯状态
	        String lampStatus=ByteUtil.toBinaryString(carStatusStr.substring(startIndex+12, startIndex+14));
	        // 指示灯状态
	        String indicatorLampStatus=ByteUtil.toBinaryString(carStatusStr.substring(startIndex+14, startIndex+16));
	        // 门状态
	        String doorStatus=ByteUtil.toBinaryString(carStatusStr.substring(startIndex+16, startIndex+18));
	        // 档位状态
	        String gearShiftStatus=carStatusStr.substring(startIndex+18, startIndex+20);
	        // 遥控器状态
	        String remoteControlStatus=carStatusStr.substring(startIndex+20, startIndex+22);
	        // 窗户状态
	        String windowStatus=ByteUtil.toBinaryString(carStatusStr.substring(startIndex+22, startIndex+24));
            resultList.add(new CarStatus(timeStamps, lampStatus,
                    indicatorLampStatus, doorStatus, gearShiftStatus,
                    remoteControlStatus, windowStatus));
	    }
	    return resultList; 
	}
    
	/**
	 * @see CommonUpStream 实体信息解析
	 * @param dataStream,已经解包的
	 * @return
	 */
	public static CommonUpStream parseCommonUpStream(byte[] dataStream) {
	    String hexString = StringUtil.toHexStringPadded(dataStream);
	    String commandType=parseCommandType(dataStream);
	    String msgProperty=parseMsgProperty(hexString);
	    String sn=parseSN(hexString);
	    String msgNumber=parseMsgNumber(hexString);
	    String checkCode=parseCheckCode(dataStream);
	    String commandFull=hexString+checkCode;
	    String msgBody = parseMsgBody(hexString);
	    String responseNumber=msgBody.substring(0, 4);
	    String responseId=msgBody.substring(4, 8);
	    String responseResult=msgBody.substring(8, 10);
        CommonUpStream commonUpStream = new CommonUpStream(
                commandType, msgProperty, sn, msgNumber, msgBody, checkCode, dataStream, commandFull, 
                responseId, responseNumber, responseResult);
		return commonUpStream;
	}
	
	/**
	 * @see TimingUpStream 实体信息解析
	 * @param dataStream,已经解包的
	 * @return
	 */
    public static TimingUpStream parseTimingUpStream(byte[] dataStream) {
	    String hexString = StringUtil.toHexStringPadded(dataStream);
	    String commandType=parseCommandType(dataStream);
	    String msgProperty=parseMsgProperty(hexString);
	    String sn=parseSN(hexString);
	    String msgNumber=parseMsgNumber(hexString);
	    String checkCode=parseCheckCode(dataStream);
	    String commandFull=hexString+checkCode;
	    String msgBody = parseMsgBody(hexString);
	    
	    //解析消息体部分
	    //时间戳
	    String timeStampStr=msgBody.substring(0, 12);
	    Timestamp timeStamps=parseGMTate(timeStampStr);
	    //行程ID 
	    String tripCount=msgBody.substring(12, 16);
	    //操作信息/设备拔插次数UUID,U16
	    String uuidStr=msgBody.substring(16, 20);
	    Long uuid=ByteUtil.hexString2Long(uuidStr);
	    //状态标识/ACC及GPS标识,U8
	    String statusFlag=msgBody.substring(20, 22);
	    String statusFlagBinary = ByteUtil.toBinaryString(statusFlag);
	    //位置信息17  
	    String gpsStr=msgBody.substring(22, 56);
	    GPS gps=parseGPS(gpsStr);
	    
	    //基站信息-国家代码U16
	    String countyCode=msgBody.substring(56, 60);
	    //基站信息-网络代码U8
	    String telecomCode=msgBody.substring(60, 62);
	    //基站信息-基站个数U8 
	    String stationNumberStr=msgBody.substring(62, 64);
	    int stationNumber=ByteUtil.hexString2Int(stationNumberStr);
	    //基站信息-基站码/小区码/信号强度5*U8*(int)stationNumber
	    int A=64+5*2*stationNumber;
	    String stationInfoListStr=msgBody.substring(64, A);
	    List<StationInfo> stationInfoList=parseStationInfoList(stationNumber,stationInfoListStr);
	    byte[] stationInfoListByte = ByteUtil.hexString2Bytes(stationInfoListStr);
	    //报警状态U32
	    String alarmStatus=msgBody.substring(A, A+4*2);
	    //车型U16
	    String modelId=msgBody.substring(A+4*2, A+6*2);
	    //数据流-数据流个数U8 
	    String pidCountStr=msgBody.substring(A+6*2, A+7*2);
	    int pidCount=ByteUtil.hexString2Int(pidCountStr);
	    //数据流-5*U8*pidCount
	    int B=(A+7*2)+5*pidCount*2;
	    String pidListStr=msgBody.substring(A+7*2, B);
	    List<Pid> pidList=parsePidList(pidCount,pidListStr);
	    byte[] pidListByte = ByteUtil.hexString2Bytes(pidListStr);
	    
	    TimingUpStream timingUpStream = new TimingUpStream(
	            commandType, msgProperty, sn, msgNumber, msgBody, checkCode, dataStream, commandFull, 
	            timeStamps,tripCount,uuid,statusFlagBinary,gps,
	            countyCode, telecomCode, stationNumber, stationInfoListByte, alarmStatus, modelId, pidCount, pidListByte
	            );
	    timingUpStream.setPidList(pidList);
	    timingUpStream.setStationInfoList(stationInfoList);
	    return timingUpStream;
	}
    
    /**
     * @see BasicInfoUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static BasicInfoUpStream parseBasicInfoUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        //状态标识/ACC及GPS标识,U8
        String statusFlag=msgBody.substring(20, 22);
        String statusFlagBinary = ByteUtil.toBinaryString(statusFlag);
        //位置信息17  
        String gpsStr=msgBody.substring(22, 56);
        GPS gps=parseGPS(gpsStr);
        
        //设备版本号 /软硬件版本号 String[22]
        String deviceVersionHexString=msgBody.substring(56, 56+22*2);
        String deviceVersion=ByteUtil.hexString2GBKString(deviceVersionHexString);
        //IMEI号 String[15]
        String imeiSeriesNumHexString=msgBody.substring(100, 100+15*2);
        String imeiSeriesNum=ByteUtil.hexString2GBKString(imeiSeriesNumHexString);
        //IMSI号 String[15]
        String imsiSeriesNumHexString=msgBody.substring(130, 130+15*2);
        String imsiSeriesNum=ByteUtil.hexString2GBKString(imsiSeriesNumHexString);
        //ICCID号 String[20]
        String iccidSeriesNumHexString=msgBody.substring(160, 160+20*2);
        String iccidSeriesNum=ByteUtil.hexString2GBKString(iccidSeriesNumHexString);
        //车辆信息-车型ID WORD 2
        String modelId=msgBody.substring(200, 200+2*2);
        //车辆信息-车辆VIN String[17]
        String vinCodeHexString=msgBody.substring(204, 204+17*2);
        String vinCode=ByteUtil.hexString2GBKString(vinCodeHexString);
        //车辆信息-码表里程 DWORD 4
        String mileageString=msgBody.substring(238, 238+4*2);
        Long mileage=ByteUtil.hexString2Long(mileageString);
        
        BasicInfoUpStream basicInfoUpStream = new BasicInfoUpStream(
                commandType, msgProperty, sn , msgNumber, msgBody,checkCode, dataStream, commandFull, 
                timeStamps, tripCount, uuid, statusFlagBinary, gps, 
                deviceVersion, imeiSeriesNum, imsiSeriesNum, iccidSeriesNum, modelId, vinCode, mileage);
        return basicInfoUpStream;
    }
    
    /**
     * @see CanDataUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static CanDataUpStream parseCanDataUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        //单组数据PID个数,U8
        String pidStr=msgBody.substring(20, 22);
        String pidBinaryStr = ByteUtil.toBinaryString(pidStr);
        String pidCountStrBinary="0"+pidBinaryStr.substring(1);
        //单组数据PID个数
        Integer pidCount=ByteUtil.binaryString2Int(pidCountStrBinary);
        // 数据补传标志(0|1表示实时|盲区)
        String validMark=pidBinaryStr.substring(0, 1);
        //车辆信息-车型ID
        String modelId=msgBody.substring(22, 26);
        //pid信息
        String pidListStr = msgBody.substring(26);
        byte[] pidListBytes=ByteUtil.hexString2Bytes(pidListStr);
        List<List<Pid>> pidList = parsePidListForCanDataUpStream(pidCount,pidListStr);
        
        CanDataUpStream canDataUpStream = new CanDataUpStream(commandType,
                msgProperty, sn, msgNumber, msgBody, checkCode, dataStream,
                commandFull, timeStamps, tripCount, uuid, pidCount, validMark,
                modelId, pidListBytes);
        canDataUpStream.setPidList(pidList);
        return canDataUpStream;
    }
    
    /**
     * @see CarStatusUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static CarStatusUpStream parseCarStatusUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //数据状态标识 
        String statusFlag=msgBody.substring(0, 2);
        //灯状态掩码
        String lampMask=msgBody.substring(2, 6);
        //车辆状态组解析
        List<CarStatus> carStatusList=parseCarStatus(msgBody.substring(6));
        
        CarStatusUpStream carStatusUpStream = new CarStatusUpStream(
                commandType, msgProperty, sn , msgNumber, msgBody,checkCode, dataStream, commandFull, 
                statusFlag, lampMask, carStatusList);
        return carStatusUpStream;
    }
    
    /**
     * @see AlarmUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static AlarmUpStream parseAlarmUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        //状态标识/ACC及GPS标识,U8
        String statusFlag=msgBody.substring(20, 22);
        String statusFlagBinary = ByteUtil.toBinaryString(statusFlag);
        //位置信息17  
        String gpsStr=msgBody.substring(22, 56);
        GPS gps=parseGPS(gpsStr);
        
        //车辆信息-车型ID 2
        String modelId=msgBody.substring(56, 56+2*2);
        //车辆信息-车辆VIN 4
        String alarmStatus=msgBody.substring(60, 60+2*4);
        
        AlarmUpStream alarmUpStream = new AlarmUpStream(
                commandType, msgProperty, sn , msgNumber, msgBody,checkCode, dataStream, commandFull, 
                timeStamps, tripCount, uuid, statusFlagBinary, gps, 
                modelId, alarmStatus);
        return alarmUpStream;
    }
	
    /**
     * @see ErrorCodeUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static ErrorCodeUpStream parseErrorCodeUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        //数据状态标识,U8
        String statusFlag=msgBody.substring(20, 22);
        String statusFlagBinary = ByteUtil.toBinaryString(statusFlag);
        
        //故障码个数 1
        Integer errorCodeCount=ByteUtil.hexString2Int(msgBody.substring(22, 22+2*1));
        String errorCodeInfoListStr=msgBody.substring(24, 24+2*4*errorCodeCount);
        //故障码信息列表 4
        byte[] errorCodeInfoListByte=ByteUtil.hexString2Bytes(errorCodeInfoListStr);
        List<ErrorCodeInfo> errorCodeInfoList=parseErrorCodeList(errorCodeCount, errorCodeInfoListStr);
        
        ErrorCodeUpStream errorCodeUpStream = new ErrorCodeUpStream(
                commandType, msgProperty, sn , msgNumber, msgBody,checkCode, dataStream, commandFull, 
                timeStamps, tripCount, uuid, statusFlagBinary,
                errorCodeCount, errorCodeInfoListByte);
        errorCodeUpStream.setErrorCodeInfoList(errorCodeInfoList);
        return errorCodeUpStream;
    }
    
    /**
     * @see OneKeyDetectionUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static OneKeyDetectionUpStream parseOneKeyDetectionUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        
        // 一键检测数据
        // 主要系统故障检测U8*8
        msgBody= msgBody.substring(20);
        String mainSystemDetectionStr = msgBody.substring(0, 8*2);
        byte[] mainSystemDetectionBytes = ByteUtil.hexString2Bytes(mainSystemDetectionStr);
        // 主要PID数据14*5
        msgBody= msgBody.substring(8*2);
        String pidListStr=msgBody.substring(0, 14*5*2);
        int pidCount=14;
        List<Pid> pidList = parsePidList(pidCount, pidListStr);
        byte[] pidListBytes = ByteUtil.hexString2Bytes(pidListStr);
        // 主要EPID数据3*5
        msgBody= msgBody.substring(14*5*2);
        pidListStr=msgBody.substring(0, 3*5*2);
        pidCount=3;
        List<Pid> epidList = parsePidList(pidCount, pidListStr);
        byte[] epidListBytes = ByteUtil.hexString2Bytes(msgBody.substring(0, 3*5*2));
        OneKeyDetectionUpStream oneKeyDetectionUpStream = new OneKeyDetectionUpStream(
                commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull, timeStamps, tripCount, uuid,
                mainSystemDetectionStr, mainSystemDetectionBytes, pidList,
                pidListBytes, epidList, epidListBytes);
        return oneKeyDetectionUpStream;
    }
    
    /**
     * @see FullCarScanUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static FullCarScanUpStream parseFullCarScanUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        
        // 读取全车支持的PID数据 5*N
        String pidListStr=msgBody.substring(20);
        int pidCount=pidListStr.length()/5*2;
        List<Pid> pidList = parsePidList(pidCount, pidListStr);
        byte[] pidListBytes = ByteUtil.hexString2Bytes(pidListStr);
        FullCarScanUpStream fullCarScanUpStream = new FullCarScanUpStream(
                commandType, msgProperty, sn, msgNumber, msgBody, checkCode,
                dataStream, commandFull, timeStamps, tripCount, uuid, pidList,
                pidListBytes);
        return fullCarScanUpStream;
    }
    
    /**
     * @see QueryParametersUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static QueryParametersUpStream parseQueryParametersUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //应答指令流水号
        String responseNumber=msgBody.substring(0, 4);
        //参数信息 
        String parameters=msgBody.substring(4);
        
        //个数计算U8
        Integer parameterCount=ByteUtil.hexString2Int(parameters.substring(0,2));
        // 参数信息集合
        List<Parameter> parameterList=parseParameterList(parameterCount,parameters);
        
        QueryParametersUpStream queryParametersUpStream = new QueryParametersUpStream(
                commandType, msgProperty, sn , msgNumber, msgBody,checkCode, dataStream, commandFull, 
                responseNumber, parameters,parameterCount,parameterList);
        return queryParametersUpStream;
    }
    
    /**
     * @see QueryLocationUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static QueryLocationUpStream parseQueryLocationUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        String responseNumber=msgBody.substring(0, 4);
        msgBody=msgBody.substring(4);
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        //状态标识/ACC及GPS标识,U8
        String statusFlag=msgBody.substring(20, 22);
        String statusFlagBinary = ByteUtil.toBinaryString(statusFlag);
        //位置信息17  
        String gpsStr=msgBody.substring(22, 56);
        GPS gps=parseGPS(gpsStr);
        
        //基站信息-国家代码U16
        String countyCode=msgBody.substring(56, 60);
        //基站信息-网络代码U8
        String telecomCode=msgBody.substring(60, 62);
        //基站信息-基站个数U8 
        String stationNumberStr=msgBody.substring(62, 64);
        int stationNumber=ByteUtil.hexString2Int(stationNumberStr);
        //基站信息-基站码/小区码/信号强度5*U8*(int)stationNumber
        int A=64+5*2*stationNumber;
        String stationInfoListStr=msgBody.substring(64, A);
        List<StationInfo> stationInfoList=parseStationInfoList(stationNumber,stationInfoListStr);
        byte[] stationInfoListByte = ByteUtil.hexString2Bytes(stationInfoListStr);
        //报警状态U32
        String alarmStatus=msgBody.substring(A, A+4*2);
        //车型U16
        String modelId=msgBody.substring(A+4*2, A+6*2);
        //数据流-数据流个数U8 
        String pidCountStr=msgBody.substring(A+6*2, A+7*2);
        int pidCount=ByteUtil.hexString2Int(pidCountStr);
        //数据流-5*U8*pidCount
        int B=(A+7*2)+5*pidCount*2;
        String pidListStr=msgBody.substring(A+7*2, B);
        List<Pid> pidList=parsePidList(pidCount,pidListStr);
        byte[] pidListByte = ByteUtil.hexString2Bytes(pidListStr);
        
        QueryLocationUpStream queryLocationUpStream = new QueryLocationUpStream(
                commandType, msgProperty, sn, msgNumber, msgBody, checkCode, dataStream, commandFull, responseNumber, 
                timeStamps,tripCount,uuid,statusFlagBinary,gps,
                countyCode, telecomCode, stationNumber, stationInfoListByte, alarmStatus, modelId, pidCount, pidListByte
                );
        queryLocationUpStream.setPidList(pidList);
        queryLocationUpStream.setStationInfoList(stationInfoList);
        return queryLocationUpStream;
    }
    
    /**
     * @see TripUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    @SuppressWarnings("deprecation")
    public static TripUpStream parseTripUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //开始时间
        String beginDatetimeStr=msgBody.substring(0, 12);
        Timestamp beginDatetime=parseGMTate(beginDatetimeStr);
        //结束时间
        String endDatetimeStr=msgBody.substring(12, 24);
        Timestamp endDatetime=parseGMTate(endDatetimeStr);
        msgBody=msgBody.substring(12);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        //状态标识/ACC及GPS标识,U8
        String statusFlag=msgBody.substring(20, 22);
        String statusFlagBinary = ByteUtil.toBinaryString(statusFlag);
        //位置信息17  
        String gpsStr=msgBody.substring(22, 56);
        GPS gps=parseGPS(gpsStr);
        
        // 经济性数据u4*4
        msgBody=msgBody.substring(56);
        // 行程里程 U4
        Float tripMileage=ByteUtil.hexString2Float(msgBody.substring(0, 8));
        // 行程油耗
        Float tripFuel=ByteUtil.hexString2Float(msgBody.substring(8, 16));
        // 行驶时间
        Float tripTime=ByteUtil.hexString2Float(msgBody.substring(16, 24));
        // 怠速时间
        Float idleTime=ByteUtil.hexString2Float(msgBody.substring(24, 32));

        // 危险驾驶行为统计 u2*6
        msgBody=msgBody.substring(32);
        // 急加速次数
        Integer hurrySpeedup=ByteUtil.hexString2Int(msgBody.substring(0, 4));
        // 急减速次数
        Integer hurryBrake=ByteUtil.hexString2Int(msgBody.substring(4, 8));
        // 急转弯次数
        Integer hurryChange=ByteUtil.hexString2Int(msgBody.substring(8, 12));
        // 超速次数
        Integer overSpeed=ByteUtil.hexString2Int(msgBody.substring(12, 16));
        // 超速时长
        Long overSpeedTime=ByteUtil.hexString2Long(msgBody.substring(16, 20));
        // 刹车次数
        Integer brakeTime=ByteUtil.hexString2Int(msgBody.substring(20, 24));

        // 数据统计U2*6
        msgBody=msgBody.substring(24);
        // 最高速度
        Float maxSpeed=ByteUtil.hexString2Float(msgBody.substring(0, 4));
        // 平均速度
        Float avgSpeed=ByteUtil.hexString2Float(msgBody.substring(4, 8));
        // 最高转速
        Float maxRotateSpeed=ByteUtil.hexString2Float(msgBody.substring(8, 12));
        // 平均转速
        Float avgRotateSpeed=ByteUtil.hexString2Float(msgBody.substring(12, 16));
        // 最大油门开度
        Float maxGasOpen=ByteUtil.hexString2Float(msgBody.substring(16, 20));
        // 平均油门开度
        Float avgGasOpen=ByteUtil.hexString2Float(msgBody.substring(20, 24));

        // 速度(S)里程统计U4*7
        msgBody=msgBody.substring(24);
        byte[] speedMileageStatistics=ByteUtil.hexString2Bytes(msgBody.substring(0, 56));
        // 转速(R)里程统计U4*6
        msgBody=msgBody.substring(56);
        byte[] rotateSpeedMileageStatistics=ByteUtil.hexString2Bytes(msgBody.substring(0, 48));;
        // 油门(O)里程统计U4*5
        msgBody=msgBody.substring(48);
        byte[] gasMileageStatistics=ByteUtil.hexString2Bytes(msgBody.substring(0, 40));;
        
        TripUpStream tripUpStream = new TripUpStream(commandType, msgProperty,
                sn, msgNumber, msgBody, checkCode, dataStream, commandFull,
                beginDatetime, endDatetime, tripCount, uuid, statusFlagBinary,
                gps, tripMileage, tripFuel, tripTime, idleTime, hurrySpeedup,
                hurryBrake, hurryChange, overSpeed, overSpeedTime, brakeTime,
                maxSpeed, avgSpeed, maxRotateSpeed, avgRotateSpeed, maxGasOpen,
                avgGasOpen, speedMileageStatistics,
                rotateSpeedMileageStatistics, gasMileageStatistics);
        return tripUpStream;
    }
    
    /**
     * @see PointCompensateUpStream 实体信息解析
     * @param dataStream,已经解包的
     * @return
     */
    public static PointCompensateUpStream parsePointCompensateUpStream(byte[] dataStream) {
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String commandType=parseCommandType(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        
        //解析消息体部分
        //时间戳
        String timeStampStr=msgBody.substring(0, 12);
        Timestamp timeStamps=parseGMTate(timeStampStr);
        //行程ID 
        String tripCount=msgBody.substring(12, 16);
        //操作信息/设备拔插次数UUID,U16
        String uuidStr=msgBody.substring(16, 20);
        Long uuid=ByteUtil.hexString2Long(uuidStr);
        //状态标识/ACC及GPS标识,U8
        String statusFlag=msgBody.substring(20, 22);
        String statusFlagBinary = ByteUtil.toBinaryString(statusFlag);
        //位置信息17  
        String gpsStr=msgBody.substring(22, 56);
        GPS gps=parseGPS(gpsStr);
        
        PointCompensateUpStream pointCompensateUpStream = new PointCompensateUpStream(
                commandType, msgProperty, sn , msgNumber, msgBody,checkCode, dataStream, commandFull, 
                timeStamps, tripCount, uuid, statusFlagBinary, gps);
        return pointCompensateUpStream;
    }
    
    /**
     * 通用的解析上行数据实体入口
     * 具体适用实体信息解析as follows
     * @see LoginUpStream 
     * HeatbeatUpStream 
     * SleepWakeUpStream 
     * SleepInUpStream 
     * 
     * @param commantType
     * @param dataStream,已经解包的
     * @return
     */
    public static UpStream parseNoMsgBodyUpStream(String commandType,
            byte[] dataStream) {
        //String commandType=parseCommandType(dataStream);
        String hexString = StringUtil.toHexStringPadded(dataStream);
        String msgProperty=parseMsgProperty(hexString);
        String sn=parseSN(hexString);
        String msgNumber=parseMsgNumber(hexString);
        String checkCode=parseCheckCode(dataStream);
        String commandFull=hexString+checkCode;
        String msgBody = parseMsgBody(hexString);
        switch (commandType) {
        //上行指令
        case StreamConstant.COMMAND_TYPE_0102:
            LoginUpStream loginUpStream = new LoginUpStream(
                    commandType,msgProperty,sn,msgNumber,msgBody,checkCode,dataStream,commandFull);
            return loginUpStream;
        case StreamConstant.COMMAND_TYPE_0002:
            HeatbeatUpStream heatbeatUpStream = new HeatbeatUpStream(
                    commandType,msgProperty,sn,msgNumber,msgBody,checkCode,dataStream,commandFull);
            return heatbeatUpStream;
        case StreamConstant.COMMAND_TYPE_0212:
            SleepWakeUpStream sleepWakeUpStream = new SleepWakeUpStream(
                    commandType,msgProperty,sn,msgNumber,msgBody,checkCode,dataStream,commandFull);
            return sleepWakeUpStream;
        case StreamConstant.COMMAND_TYPE_0213:
            SleepInUpStream sleepInUpStream = new SleepInUpStream(
                    commandType,msgProperty,sn,msgNumber,msgBody,checkCode,dataStream,commandFull);
            return sleepInUpStream;
        default:
            throw new ParseStreamException("parseNoMsgBodyUpStream解析上行数据失败，找不到对应的数据类型!");
        }
        
    }
    
    /**
     * 把实体bean对象转换成DBObject
     */
    public static <T> DBObject bean2DBObject(T bean)
            throws IllegalArgumentException, IllegalAccessException {
        if (bean == null) {
            return null;
        }
        DBObject dbObject = new BasicDBObject();
        //递归调用,获取对象对应类中的所有属性
        Field[] fields =null;
        Class<?> clazz = bean.getClass();
        for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
            fields =clazz.getDeclaredFields();
            for (Field field : fields) {
                // 获取属性名
                String fieldName = field.getName();
                String genericTypeName = field.getGenericType().toString();
//                Class<?> declaringClass = field.getDeclaringClass();
                if("serialVersionUID".equalsIgnoreCase(fieldName)
                        ||"logger".equalsIgnoreCase(fieldName)
                        ||"interface org.slf4j.Logger".equalsIgnoreCase(genericTypeName)
//                        ||Logger.class.isAssignableFrom(declaringClass)
                        ){
                    continue;
                }
                // 修改访问控制权限
                boolean accessFlag = field.isAccessible();
                if (!accessFlag) {
                    field.setAccessible(true);
                }
                Object param = field.get(bean);
                if (param == null) {
                    field.setAccessible(accessFlag);
                    continue;
                }else{
                    Object instanceofFieldValue = instanceofFieldValue(param);
                    if(instanceofFieldValue!=null){
                        dbObject.put(fieldName,instanceofFieldValue);
                    }else{
                        dbObject.put(fieldName,"");
                    }
                }
                // 恢复访问控制权限
                field.setAccessible(accessFlag);
            }
        }
        
        return dbObject;
    }
    
    /**
     * 转换数据类型
     */
    private static Object instanceofFieldValue(Object param) 
            throws IllegalArgumentException, IllegalAccessException {
        if (param == null) {
            return null;
        // 判断变量的类型
        } else if (param instanceof String) {
            String value = (String) param;
            return value;
        } else if (param instanceof Integer) {
            int value = ((Integer) param).intValue();
            return value;
        } else if (param instanceof Double) {
            double value = ((Double) param).doubleValue();
            return value;
        } else if (param instanceof Float) {
            float value = ((Float) param).floatValue();
            return value;
        } else if (param instanceof Long) {
            long value = ((Long) param).longValue();
            return value;
        } else if (param instanceof Short) {
            Short value = ((Short) param).shortValue();
            return value;
        } else if (param instanceof Boolean) {
            boolean value = ((Boolean) param).booleanValue();
            return value;
        } else if (param instanceof Byte) {
            Byte value = ((Byte) param).byteValue();
            return value;
        }else if (param instanceof Character) {
            Character value = ((Character) param).charValue();
            return value;
        }else if (param instanceof Timestamp) {
            Timestamp value = (Timestamp) param;
            return value;
        }else if (param instanceof Date) {
            Date value = (Date) param;
            return value;
        }else if(param instanceof MessageEntity){
            // 处理实体BaseMessageEntity,Map和List情况;
            return bean2DBObject(param);
        }else if(param instanceof List){
//      BasicDBList list = new BasicDBList();
            List<DBObject> list = new ArrayList<DBObject>();
            for (Object item : (List<?>)param) {
                list.add(bean2DBObject(item));
            }
            return list;
        }else if(param instanceof Map){
            @SuppressWarnings("rawtypes")
            Map value = (Map) param;
            return new BasicDBObject(value);
        }else if(param.getClass().isArray()){
            return parseArray(param);
        }
        return null;
    }
    
    /**
     * 解析数组数据
     */
    private static Object parseArray(Object param) {
        // 方式1:处理数组信息Object[],针对数组类型数据进行处理
        Class<?> componentType = param.getClass().getComponentType();
        if(componentType.isAssignableFrom(Byte.class)
                ||componentType.isAssignableFrom(byte.class)){
            //下行数据流包含的格式
            String jsonString = JSON.toJSONString(param);
            return jsonString;
        }else if(componentType.isAssignableFrom(Character.class)
                ||componentType.isAssignableFrom(char.class)){
          String jsonString = JSON.toJSONString(param);
          return jsonString;
        }else if(componentType.isAssignableFrom(Boolean.class)
                ||componentType.isAssignableFrom(boolean.class)){
            
        }else if(componentType.isAssignableFrom(Short.class)
                ||componentType.isAssignableFrom(short.class)){
            
        }else if(componentType.isAssignableFrom(Integer.class)
                ||componentType.isAssignableFrom(int.class)){
            
        }else if(componentType.isAssignableFrom(Long.class)
                ||componentType.isAssignableFrom(long.class)){
            
        }else if(componentType.isAssignableFrom(Float.class)
                ||componentType.isAssignableFrom(float.class)){
            
        }else if(componentType.isAssignableFrom(Double.class)
                ||componentType.isAssignableFrom(double.class)){
            
        }else if(componentType.isAssignableFrom(String.class)){
//          List<String> list = new ArrayList<String>();
//          list.add(item);
        }else if(componentType.isAssignableFrom(Timestamp.class)){
            
        }else if(componentType.isAssignableFrom(Date.class)){
            
        }else if(componentType.isAssignableFrom(MessageEntity.class)){
            
        }
        return null;
    }
    
    public static void printMessageEntityPrettyLog(Object entity) throws IllegalArgumentException, IllegalAccessException {
        StringBuilder sb=new StringBuilder();
        sb.append("\r\n");
        sb.append("\r\n********************************打印实体信息开始********************************\r\n");
        printMessageEntityLog(entity,sb,"");
        sb.append("********************************打印实体信息结束********************************\r\n");
        logger.warn(""+ sb.toString());
    }
    
    /**
     * 解析实体信息
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    @SuppressWarnings("rawtypes")
    private static void printMessageEntityLog(Object entity,StringBuilder sb,String prefix) 
            throws IllegalArgumentException, IllegalAccessException {
        if(entity==null){
            return;
        }
        
        //Map数据判断
        if(entity instanceof Map){
            Map map = (Map)entity;
            sb.append(prefix+"{\r\n");
            for (Object key : map.keySet()) {
                String value = MyStringUtil.getStringValue(map.get(key));
                sb.append("\""+key+"\":"+"\""+value+"\"");
                sb.append("\r\n");
            }
            sb.append(prefix+"}\r\n");
        }else if(entity instanceof String){
            sb.append(prefix+"{\r\n");
            sb.append("\"String\":"+"\""+MyStringUtil.getStringValue(entity)+"\"");
            sb.append("\r\n");
            sb.append(prefix+"}\r\n");
        }else if(entity instanceof Boolean){
            sb.append(prefix+"{\r\n");
            sb.append("\"Boolean\":"+"\""+MyStringUtil.getStringValue(entity)+"\"");
            sb.append("\r\n");
            sb.append(prefix+"}\r\n");
        }else if(entity instanceof Number){
            sb.append(prefix+"{\r\n");
            sb.append("\"Number\":"+"\""+MyStringUtil.getFloatValue(entity)+"\"");
            sb.append("\r\n");
            sb.append(prefix+"}\r\n");
        }else if(entity instanceof Date){
            sb.append(prefix+"{\r\n");
            sb.append("\"Date\":"+"\""+DatetimeUtil.formatDate((Date)entity)+"\"");
            sb.append("\r\n");
            sb.append(prefix+"}\r\n");
        }
        
        Class<?> clazz = entity.getClass();
        Field[] fields =null;
        for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
            fields =clazz.getDeclaredFields();
//            for (Field field : fields) {
            for (int i=0;fields!=null&&i<fields.length ; i++) {
                Field field=fields[i];
                // 获取属性名
                String fieldName = field.getName();
                String genericTypeName = field.getGenericType().toString();
                if("serialVersionUID".equalsIgnoreCase(fieldName)
                        ||"logger".equalsIgnoreCase(fieldName)
                        ||"interface org.slf4j.Logger".equalsIgnoreCase(genericTypeName)
                        ){
                    continue;
                }
                // 修改访问控制权限
                boolean accessFlag = field.isAccessible();
                if (!accessFlag) {
                    field.setAccessible(true);
                }
                if(field.isAnnotationPresent(Descriptor.class)){
                    String annotationValue = field.getAnnotation(Descriptor.class).value();
                    sb.append(prefix+annotationValue+"("+fieldName+"):");
                }else{
                    //sb.append("("prefix+fieldName+"):");
                    field.setAccessible(accessFlag);
                    continue;
                }
                Object param = field.get(entity);
                if (param == null
                        &&!("class java.lang.String".equalsIgnoreCase(genericTypeName))
                        &&!("class java.sql.Timestamp".equalsIgnoreCase(genericTypeName))
                        &&!("class java.sql.Date".equalsIgnoreCase(genericTypeName))
                        &&!(field.getType().isPrimitive())
                        ) {
                    sb.append("NULL\r\n");
                    field.setAccessible(accessFlag);
                    continue;
                }
                if(param instanceof MessageEntity){
                    //首先判断属性是否为实体bean,迭代调用
                    sb.append("\r\n"+prefix+"{\r\n");
                    printMessageEntityLog(param,sb,prefix+"\t");
                    sb.append(prefix+"}");
                }else if(param instanceof List){
                    if(param!=null){
                        List<?> paramList=(List<?>)param;
                        sb.append("\r\n[");
                        for (Object object : paramList) {
                            if(object instanceof MessageEntity){
                                sb.append("{");
                                printMessageEntityLog(object,sb,prefix+"\t");
                                sb.append("}");
                            }else{
                                Object instanceofFieldValue = instanceofFieldValue(object);
//                                sb.append(prefix);
                                sb.append(MyStringUtil.getStringValue(instanceofFieldValue));
                                //sb.append(MyStringUtil.getStringValue(param));
                            }
                        }
                        sb.append("]");
                    }
                }else{
                    Object instanceofFieldValue = instanceofFieldValue(param);
                    //sb.append(prefix);
                    if(StringUtils.isBlank(MyStringUtil.getStringValue(instanceofFieldValue))){
                        sb.append("\"\"");
                    }else{
                        sb.append(MyStringUtil.getStringValue(instanceofFieldValue));
                    }
                }
                // 恢复访问控制权限
                field.setAccessible(accessFlag);
                sb.append("\r\n");
            }
        }
    }
	
}
