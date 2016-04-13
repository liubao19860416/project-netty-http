package com.saic.ebiz.vbox.conf;

import io.netty.util.AttributeKey;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


/**
 * 定义的一些常量信息
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年10月13日
 *
 */
public final class StreamConstant {
    
    public static final byte[] STREAM_7D = new byte[] { 0x7D };
    public static final byte[] STREAM_7E = new byte[] { 0x7E };
    public static final byte _7D = 0x7D;
    public static final byte _7E = 0x7E;
    public static final byte _01 = 0x01;
    public static final byte _02 = 0x02;
    public static final byte _0 = 0x30;
    public static final byte _9 = 0x39;
    
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;
    public static final int TRUE_NUMBER = 1;
    public static final int FALSE_NUMBER = 0;
    public static final String ERROR = "ERROR";
    public static final String NULL = "NULL";
    public static final String OK = "OK";
    public static final String EMPTY = "";
    public static final Charset CHARSET_DEFAULT = Charset.forName("GBK");
    
    public static final AttributeKey<String> ATTRIBUTEKEY = AttributeKey.valueOf("device.sn");
    //存储到mongodb的对应的实体的key前缀
    public static final String GPRS_MONGODB_UP_COLLNAME = "gprs_upstream_history";
    public static final String GPRS_MONGODB_DOWN_COLLNAME = "gprs_downstream_history";
    public static final String GPRS_MONGODB_ERRORE_COLLNAME = "gprs_error_history";
    public static final String GPRS_MONGODB_TYPE_PREFIX = "gprs_";
    public static final String SERVER_DOMAIN = NULL;
    public static final long SERVER_PORT = 28801L;
    public static final String SERVER_IP = "";

    // ASCII:~==0x7E
    public static final byte[] STREAM_DELIMITERS = new byte[]{0x7E};
//	public static final byte[] STREAM_DELIMITERS = new byte[]{'~'};
	public static final byte STREAM_SEPARATE = ',';
    public static final String MARK_BIT = "7E";
//    public static final String SN_PREFIX = "0";
    public static final int MIN_LENGTH = 13;//不包含标识位,数据体和校验码部分
    public static final String OK_RESPONSERESULT = "00";
    public static final String MSGNUMBER_DEFAULT = "FFFF";
    public static final String MSGPROPERTY_0000 = "0000";
    public static final String MSGPROPERTY_0001 = "0001";
    public static final String MSGPROPERTY_0005 = "0005";
    public static final String MSGPROPERTY_000A = "000A";
    
	//定义对应的key请求命令字常量信息
    //============上行指令============
    //设备通用应答
	public static final String COMMAND_TYPE_0001 = "0001";
	//登录报文
    public static final String COMMAND_TYPE_0102 = "0102";
    //心跳包
    public static final String COMMAND_TYPE_0002 = "0002";
    //定时报告
    public static final String COMMAND_TYPE_0200 = "0200";
    //基本信息包
    public static final String COMMAND_TYPE_0206 = "0206";
    //CAN数据连续包
    public static final String COMMAND_TYPE_0208 = "0208";
    //车身状态信息
    public static final String COMMAND_TYPE_0214 = "0214";
    //报警数据
    public static final String COMMAND_TYPE_0201 = "0201";
    //故障码信息
    public static final String COMMAND_TYPE_0209 = "0209";
    //一键检测应答
    public static final String COMMAND_TYPE_0220 = "0220";
    //全车扫描应答
    public static final String COMMAND_TYPE_0221 = "0221";
    //行程数据包
    public static final String COMMAND_TYPE_0205 = "0205";
    //拐点补偿数据
    public static final String COMMAND_TYPE_0215 = "0215";
    //休眠唤醒
    public static final String COMMAND_TYPE_0212 = "0212";
    //休眠进入
    public static final String COMMAND_TYPE_0213 = "0213";
    //透传指令-设备上行
    public static final String COMMAND_TYPE_0900 = "0900";
    //查询参数应答
    public static final String COMMAND_TYPE_0104 = "0104";
    //位置信息查询应答
    public static final String COMMAND_TYPE_0202 = "0202";
    //============下行指令============
    //平台通用应答
    public static final String COMMAND_TYPE_8001 = "8001";
    //登录应答
    public static final String COMMAND_TYPE_8102 = "8102";
    //透传指令-平台下行 暂时不做
    public static final String COMMAND_TYPE_8900 = "8900";
    
    
    //位置信息查询
    public static final String COMMAND_TYPE_8201 = "8201";
    //查询终端属性
    public static final String COMMAND_TYPE_8107 = "8107";
    //查询车况信息
    public static final String COMMAND_TYPE_8B00 = "8B00";
    //清除报警消息
    public static final String COMMAND_TYPE_8203 = "8203";
    
    //定义请求命令commandType对应的指令名称commandName描述信息
    //============上行指令============
    public static final String COMMAND_NAME_0001 = "设备通用应答";
    public static final String COMMAND_NAME_0102 = "登录报文";
    public static final String COMMAND_NAME_0002 = "心跳包";
    public static final String COMMAND_NAME_0200 = "定时报告";
    public static final String COMMAND_NAME_0206 = "基本信息包";
    public static final String COMMAND_NAME_0208 = "CAN数据连续包";
    public static final String COMMAND_NAME_0214 = "车身状态信息";
    public static final String COMMAND_NAME_0201 = "报警数据";
    public static final String COMMAND_NAME_0209 = "故障码信息";
    public static final String COMMAND_NAME_0220 = "一键检测应答";
    public static final String COMMAND_NAME_0221 = "全车扫描应答";
    public static final String COMMAND_NAME_0205 = "行程数据包";
    public static final String COMMAND_NAME_0215 = "拐点补偿数据";
    public static final String COMMAND_NAME_0212 = "休眠唤醒";
    public static final String COMMAND_NAME_0213 = "休眠进入";
    public static final String COMMAND_NAME_0900 = "透传指令-设备上行";
    public static final String COMMAND_NAME_0104 = "查询参数应答";
    public static final String COMMAND_NAME_0202 = "位置信息查询应答";
    //============下行指令============
    public static final String COMMAND_NAME_8001 = "平台通用应答";
    public static final String COMMAND_NAME_8102 = "登录应答";
    public static final String COMMAND_NAME_8900 = "透传指令-平台下行";
    public static final String COMMAND_NAME_8201 = "位置信息查询";
    public static final String COMMAND_NAME_8107 = "查询终端属性";
    public static final String COMMAND_NAME_8B00 = "查询车况信息";
    public static final String COMMAND_NAME_8203 = "清除报警消息";
    
    //构建规则信息map
    public final static Map<String, String> upMappingDownRulesMap = new HashMap<String, String>();
    //命令字commandKey对应的命令名称commandName描述信息
    public final static Map<String, String> commandTypeMappingNameMap = new HashMap<String, String>();
    
    static {
        //============上行指令============
        upMappingDownRulesMap.put(COMMAND_TYPE_0001, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_0102, COMMAND_TYPE_8102);
        upMappingDownRulesMap.put(COMMAND_TYPE_0002, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0200, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0206, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0208, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0214, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0201, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0209, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0220, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0221, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0205, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0215, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0212, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0213, COMMAND_TYPE_8001);
        upMappingDownRulesMap.put(COMMAND_TYPE_0900, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_0104, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_0202, "");
        
        //============下行指令============
        upMappingDownRulesMap.put(COMMAND_TYPE_8001, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_8102, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_8900, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_8201, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_8107, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_8B00, "");
        upMappingDownRulesMap.put(COMMAND_TYPE_8203, "");
    }
    
    static{
        commandTypeMappingNameMap.put(COMMAND_TYPE_0001, COMMAND_NAME_0001);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0102, COMMAND_NAME_0102);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0002, COMMAND_NAME_0002);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0200, COMMAND_NAME_0200);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0206, COMMAND_NAME_0206);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0208, COMMAND_NAME_0208);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0214, COMMAND_NAME_0214);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0201, COMMAND_NAME_0201);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0209, COMMAND_NAME_0209);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0220, COMMAND_NAME_0220);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0221, COMMAND_NAME_0221);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0205, COMMAND_NAME_0205);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0215, COMMAND_NAME_0215);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0212, COMMAND_NAME_0212);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0213, COMMAND_NAME_0213);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0900, COMMAND_NAME_0900);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0104, COMMAND_NAME_0104);
        commandTypeMappingNameMap.put(COMMAND_TYPE_0202, COMMAND_NAME_0202);
        
        commandTypeMappingNameMap.put(COMMAND_TYPE_8001, COMMAND_NAME_8001);
        commandTypeMappingNameMap.put(COMMAND_TYPE_8102, COMMAND_NAME_8102);
        commandTypeMappingNameMap.put(COMMAND_TYPE_8900, COMMAND_NAME_8900);
        commandTypeMappingNameMap.put(COMMAND_TYPE_8201, COMMAND_NAME_8201);
        commandTypeMappingNameMap.put(COMMAND_TYPE_8107, COMMAND_NAME_8107);
        commandTypeMappingNameMap.put(COMMAND_TYPE_8B00, COMMAND_NAME_8B00);
        commandTypeMappingNameMap.put(COMMAND_TYPE_8203, COMMAND_NAME_8203);
    }
   
}
