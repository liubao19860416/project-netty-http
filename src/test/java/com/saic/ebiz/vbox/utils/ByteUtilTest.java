package com.saic.ebiz.vbox.utils;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saic.ebiz.vbox.exception.ChecksumException;

/**
 * 针对byte数组类型数据操作的工具类测试单元
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
public class ByteUtilTest {
    
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ByteUtilTest.class);
    
    /**
     * 01020101 010203040506070101
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testMain() throws ChecksumException {
        String str="EF3D5B5CC2EAAEECECD2BD8CC8AD12A3BF900ADA";
        System.out.println("转换结果为:"+new String(ByteUtil.hexString2Bytes(str),Charset.forName("UTF-8")));
//      byte[] src=new byte[]{0x01,0x02,0x01,0x01,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x01,0x01,'1',0x30};
        byte[] src=new byte[]{0x01,0x02,0x00,0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x01,0x02};
        String bytes2HexString = ByteUtil.bytes2HexString(src);
        System.out.println(bytes2HexString);
        byte[] hexString2Bytes = ByteUtil.hexString2Bytes(bytes2HexString);
        System.out.println(Arrays.toString(src));
        System.out.println(Arrays.toString(hexString2Bytes));
        System.out.println(ByteUtil.bytes2HexString(hexString2Bytes));
        //System.out.println(new String(hexString2Bytes));
        System.out.println(ByteUtil.calculateCheckCode(src));
        System.out.println(ByteUtil.calculateCheckCode(hexString2Bytes));
        System.out.println(ByteUtil.bytes2HexString(new byte[]{ByteUtil.calculateCheckCode(src)}));//00
        //src[src.length]=0x03;
        src=new byte[]{0x01,0x02,0x00,0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x01,0x02,0x00};
        boolean check = ByteUtil.check(src);
        System.out.println(check);
        System.out.println(ByteUtil.bytes2HexString(hexString2Bytes,0,2));
        System.out.println(ByteUtil.bytes2HexString(hexString2Bytes,2));
        
        bytes2HexString = "0F";
        System.out.println(bytes2HexString);
        hexString2Bytes = ByteUtil.hexString2Bytes(bytes2HexString);
        System.out.println(Arrays.toString(hexString2Bytes));
        System.out.println(hexString2Bytes[0]);
        System.out.println(ByteUtil.byteToInt(hexString2Bytes[0]));
        System.out.println(Integer.toHexString(11));
        String encodeHexString = Hex.encodeHexString(new byte[]{0x0b,0x0d,0x0F});
        System.out.println(encodeHexString);
        System.out.println("hexString2Int:"+ByteUtil.hexString2Int("018F"));
        System.out.println("hexString2Long:"+ByteUtil.hexString2Long("018F"));
//        System.out.println("hexString2Float:"+ByteUtil.hexString2Float("018F"));
        System.out.println("intToByte:"+ByteUtil.intToByte(11));
        System.out.println("Float.toHexString:"+Float.toHexString(15f));//0x1.ep3
        System.out.println("Integer.toHexString:"+Integer.toHexString(156));
        System.out.println("Integer.toBinaryString:"+Integer.toBinaryString(156));
        System.out.println("Long.toBinaryString:"+Long.toBinaryString(156));
        System.out.println("toBinary16String:"+ByteUtil.toBinary16String(11));
        System.out.println("toBinary32String:"+ByteUtil.toBinary32String(11));
        System.out.println("toBinaryString:"+ByteUtil.toBinaryString("01"));
        System.out.println("toBinaryString:"+ByteUtil.toBinaryString("E"));
        System.out.println("toBinaryString:"+ByteUtil.toBinaryString("018F"));
        //00000001 00000001=2`8+1=4*4*4*4+1=16*16+1=256+1
        System.out.println(ByteUtil.byteArray2Int(new byte[]{0x01,0x01}));
        System.out.println(ByteUtil.byteArray2Long(new byte[]{0x01,0x01}));
//        System.out.println(Arrays.toString(longToByteArray(15)));
//        System.out.println(bytes2HexString(longToByteArray(15),8));
//        System.out.println(StringUtil.toHexString(longToByteArray(15)));
//        System.out.println("BCDCodeToDecimalDigit:"+BCDCodeToDecimalDigit("0101"));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(new byte[]{00,01,01,01}));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(ByteUtil.hexString2Bytes("00010101")));//15
        System.out.println("BCDCodeToDecimalDigit:"+Arrays.toString(new byte[]{00,01,01,01}));
        System.out.println("BCDCodeToDecimalDigit:"+Arrays.toString(ByteUtil.hexString2Bytes("00010101")));
        
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(new byte[]{00,01,00,00}));
        System.out.println("BCDCodeToDecimalDigit:"+Arrays.toString(ByteUtil.str2Bcd("10")));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.toBinaryString("10"));
        //HEX:16===>BCD:00010110===>16
        //HEX:01===>BCD:00000001===>01
        //HEX:25===>BCD:00100101===>25
        //HEX:23===>BCD:00100011===>23
        //HEX:59===>BCD:01011001===>59
        //HEX:00===>BCD:00000000===>00
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(ByteUtil.hexString2Bytes("16")));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(ByteUtil.hexString2Bytes("01")));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(ByteUtil.hexString2Bytes("25")));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(ByteUtil.hexString2Bytes("23")));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(ByteUtil.hexString2Bytes("59")));
        System.out.println("BCDCodeToDecimalDigit:"+ByteUtil.bcd2Str(ByteUtil.hexString2Bytes("00")));
        System.out.println("floatToByte:"+Hex.encodeHexString(ByteUtil.floatToByte(14.5f,0)));//00006841
        System.out.println("hexString2Bytes:"+Arrays.toString(ByteUtil.hexString2Bytes("00006841")));
//        System.out.println("byteToFloat:"+ByteUtil.byteU32ToFloat(ByteUtil.hexString2Bytes("00006841"),0));
//        System.out.println("hexStringU32ToFloat:"+ByteUtil.hexStringU32ToFloat("00006841"));
        System.out.println("floatToByte:"+Arrays.toString(ByteUtil.floatToByte(14,0)));
        System.out.println("floatToByte:"+Arrays.toString(ByteUtil.floatToByte(14.0F,0)));
        float intBitsToFloat = Float.intBitsToFloat(0x01580718);
        System.out.println("intBitsToFloat:"+intBitsToFloat);
        System.out.println("intBitsToFloat:"+MyStringUtil.formatNumber(14.678006f, 5));
        Long l=14L;
        float f=l*0.00001F;
        double d=l*0.00001;
        System.out.println("intBitsToFloat:"+l.floatValue());
        System.out.println("intBitsToFloat:"+f);
        System.out.println("intBitsToFloat:"+d);
        System.out.println("binaryString2Int:"+ByteUtil.binaryString2Int("00000101"));//5
        System.out.println("binaryString2Int:"+ByteUtil.binaryString2Int("10111"));//23
        //前面有一个空格,错误的vinCode数据
        String vinCodeHexString="0032333435363738394142434445464748";
        String vinCode=ByteUtil.hexString2GBKString(vinCodeHexString);
        System.out.println("vinCode:"+vinCode);
        byte[] bytes = new String("CXB900H100S10220160127").getBytes();
        String bytes2HexString2 = ByteUtil.bytes2HexString(bytes);
        System.out.println("bytes2HexString2:"+bytes2HexString2);
        
        
    }
    
}
