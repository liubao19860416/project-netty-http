package com.saic.ebiz.vbox.utils;

import io.netty.util.internal.StringUtil;

import java.sql.Timestamp;
import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saic.ebiz.vbox.conf.StreamConstant;

/**
 * Stream操作工具类测试单元
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
public class StreamUtilsTest {
    
    private static final Logger logger = LoggerFactory.getLogger(StreamUtilsTest.class);
    
    @Test
    public void testMain() throws Exception {
        //登录
        //byte[] src=new byte[]{0x01,0x02,0x01,0x01,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x01,0x01,0x03};
        byte[] src=new byte[]{0x01,0x02,0x00,0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x01,0x02};
        String commandType = StreamUtils.parseCommandType(src);//0102
        System.out.println(commandType);
        String hexString = StringUtil.toHexStringPadded(src);
        String MsgProperty = StreamUtils.parseMsgProperty(hexString);
        System.out.println(MsgProperty);
        String sn = StreamUtils.parseSN(hexString);
        System.out.println(sn);
        String msgNumber = StreamUtils.parseMsgNumber(hexString);
        System.out.println(msgNumber);
        String checkCode = StreamUtils.parseCheckCode(src);
        System.out.println(checkCode);
        
        //通用设备应答
//        String str="000100050102030405060701020F0E0D0C0B";//0c
//        String str="800100050102030405060703040102010200";//83
//        String str="800100050102030405060701020102010200";//87
//        String str="00020000010203040506070304";//05
//        String str="02120000010203040506070304";//17
//        String str="02130000010203040506070304";//16
//        String str="00020000123456789000000003";//99
//        String str="0200005E12345678900000000416011110300000050010030158071806CA84C30100200000100B0003008602030001000203000200030400030004050000000101000801111111110222222222033333333304444444440555555555066666666607777777770888888888";//84
//        String str="020800ad02101060400004001c160128021031000300030200000c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000a430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b430c009733470d00000b43";//82
        String str="000200001234567890abcd000a";//7E000200001234567890abcd000af67E
        byte[] hexString2Bytes = ByteUtil.hexString2Bytes(str);
        System.out.println(Arrays.toString(hexString2Bytes));
        System.out.println(str);
        System.out.println(StringUtil.toHexString(hexString2Bytes));
        checkCode = StreamUtils.parseCheckCode(hexString2Bytes);
        System.out.println("计算得到的校验码为:"+checkCode);
//        String hexGMTateStr="0e-01-01-08-02-29";
        String hexGMTateStr="0e0101080229";
        Timestamp parseGMTate = StreamUtils.parseGMTate(hexGMTateStr);
        System.out.println(DatetimeUtil.formatTimestamp(parseGMTate));
        
    }
    
    @Test
    public void testName() throws Exception {
        String commandType="";
        switch (commandType) {
        case StreamConstant.COMMAND_TYPE_8201:
        case StreamConstant.COMMAND_TYPE_8107:
        case StreamConstant.COMMAND_TYPE_8203:
            logger.info("构建无请求参数下行数据实体!");
            break;
        default: {
            logger.info("不存在对应的[commandType={},sn={}]信息,构建下行实体数据信息失败!",commandType);
        }
        }
    }
    
}
