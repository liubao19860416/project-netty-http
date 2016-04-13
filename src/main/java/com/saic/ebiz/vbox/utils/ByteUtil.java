package com.saic.ebiz.vbox.utils;

import io.netty.util.internal.StringUtil;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.exception.ChecksumException;

/**
 * 针对byte数组类型数据操作的工具类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
public class ByteUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(ByteUtil.class);
    
	/**
	 * 检查byte数组array是否以byte数组start开始
	 */
	public static boolean startWith(byte[] array, byte[] start) {
		if (ArrayUtils.isEmpty(array) || ArrayUtils.isEmpty(start)) {
			return false;
		}
		if (array.length < start.length) {
			return false;
		}
		for (int i = 0; i < start.length; i ++) {
			if(array[i] != start[i]) {
				return false;
			}
		}
		return true;
	}
	
    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
//        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
//                .toString().substring(1) : temp.toString();
        return temp.toString();
    }

    /**
     * @功能: 10进制串转为BCD码
     * @参数: 10进制串
     * @结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }
	
	/**
     * 将一个byte字节数据的字符串,转换为对应的int值
     */
    public static int binaryString2Int(String binaryString){
        return Integer.valueOf(binaryString, 2).intValue();
        //return Integer.decode("0x" +hexString).intValue();
        //return Byte.decode("0x" +hexString).intValue();
    }
    public static int hexString2Int(String hexString){
       return Integer.valueOf(hexString, 16).intValue();
       //return Integer.decode("0x" +hexString).intValue();
       //return Byte.decode("0x" +hexString).intValue();
    }
    public static long hexString2Long(String hexString){ 
        return Long.valueOf(hexString, 16).longValue();
        //return Long.decode("0x" +hexString).longValue();
    }
    
    /**
     * 本质还是Long型数据转换
     */
    @Deprecated
    public static float hexString2Float(String hexString){ 
        return Long.valueOf(hexString, 16).floatValue();
        //return Long.decode("0x" +hexString).floatValue();
    }
    
    /**
     * 将数字转换为二进制字符串
     * byte占1个字节,8位
     */
    public static String toBinary8String(int value){ 
        String binaryString = Integer.toBinaryString(value);
        int length = binaryString.length();
        while(binaryString.length()<8){
            binaryString="0"+binaryString;
        }
        if(binaryString.length()>8){
            binaryString=binaryString.substring(length-8, length);
        }
        return binaryString;
    }
    /**
     * 将数字转换为二进制字符串
     * int占2个字节,16位
     */
    public static String toBinary16String(int value){ 
        String binaryString = Integer.toBinaryString(value);
        while(binaryString.length()<8*2){
            binaryString="0"+binaryString;
        }
        return binaryString;
    }
    /**
     * long占4个字节,32位
     * @param value
     * @return
     */
    public static String toBinary32String(long value){ 
        String binaryString = Long.toBinaryString(value);
        while(binaryString.length()<8*4){
            binaryString="0"+binaryString;
        }
        return binaryString;
    }
    /**
     * 将HEX字符串转换为二进制字符串
     */
    public static String toBinaryString(String hexString){ 
        int length = hexString.length();
        String binaryString = Long.toBinaryString(hexString2Long(hexString));
        while(binaryString.length()<length*4){
            binaryString="0"+binaryString;
        }
        if(binaryString.length()>length*4){
            binaryString=binaryString.substring(
                    binaryString.length()-length*4, binaryString.length());
        }
        return binaryString;
    }
    
    /** 
     * 将指定字符串src，以每两个字符分割转换为16进制形式 
     * 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9} 
     * @param src String 
     * @return byte[] 
     */ 
    public static byte[] hexString2Bytes(String src){ 
        int mod = src.length() % 2; 
        if (mod != 0) { 
            src = "0" + src; 
        } 
        byte[] tmp = src.getBytes(); 
        int length=tmp.length/2;
        byte[] ret = new byte[length]; 
        for(int i=0,j=0; j<length; i+=2,j+=1){ 
            ret[j] = uniteBytes(tmp[i], tmp[i+1]); 
        } 
        return ret; 
    }
    
    /** 
     * 将两个ASCII字符合成一个字节； 
     * 如："EF"--> 0xEF 
     * @param src0 byte 
     * @param src1 byte 
     * @return byte 
     */ 
    private static byte uniteBytes(byte src0, byte src1) { 
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue(); 
        _b0 = (byte)(_b0 << 4); 
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue(); 
        byte ret = (byte)(_b0 ^ _b1); 
        return ret; 
    } 
    
    /**
     * Byte[]数组转换为float格式数字
     * @param b
     * @param index 第几位开始取.
     */
//    public static float byteU32ToFloat(byte[] b, int index) {
//        int l = b[index + 0];
//        l &= 0xff;
//        l |= ((long) b[index + 1] << 8);
//        l &= 0xffff;
//        l |= ((long) b[index + 2] << 16);
//        l &= 0xffffff;
//        l |= ((long) b[index + 3] << 24);
//        return Float.intBitsToFloat(l);
//    }
    
    /**
     * HEX格式字符串转换为float数值类型,hexString的长度为4个字节
     */
//    public static float hexStringU32ToFloat(String hexString) {
//        while(hexString.length()<8){
//            hexString="0"+hexString;
//        }
//        return byteU32ToFloat(hexString2Bytes(hexString),0);
//    }
  
    /**
     * float转换byte
     * @param x
     * @param index
     */
    public static byte[] floatToByte(float x, int index) {
        byte[] bb = new byte[4];
//        int l = Float.floatToIntBits(x);
        int l = Float.floatToRawIntBits(x);
        logger.warn("float[{}]转换int格式结果:{}",x,l);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer(l).byteValue();
            l = l >> 8;
        }
        logger.warn("float[{}]转换byte格式结果:{}",x,Arrays.toString(bb));
        return bb;
    }
  
    /**
     * 将byte[]数据与int型数据互转
     */
    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    public static byte intToByte(int x) {
        return (byte) x;
    }
    
    /**
     * 将byte[]数据转换为int型,可以是多个字节
     */
    public static Integer byteArray2Int(final byte[] bytes) {
        int num = 0;
        int count = 0;
        for(int i = bytes.length - 1; i >= 0; i --) {
            if (i >= 4) {
                //int型最多4个字节
                break;
            }
            num |= (int)(bytes[i]&0xFF) << (count ++ * 8);
        }
        return num;
    }
    
    /**
     * 将byte[]数据转换为long型
     */
    public static Long byteArray2Long(final byte[] bytes) {
        long l = 0;
        int count = 0;
        for(int i = bytes.length - 1; i >= 0; i --) {
            if (i >= 8) {
                //long型最多8个字节
                break;
            }
            l |= (long)(bytes[i]&0xFF) << (count ++ * 8);
        }
        return l;
    }
	
	/**
     * 将long转换为byte数组
     */
//    public static byte[] longToByteArray(long l) { 
//        long temp = l; 
//        byte[] bytes = new byte[8]; 
//        for (int i = 0; i < bytes.length; i++) { 
//            // 将最低位保存在最高位 
//            bytes[i] = new Long(temp & 0xff).byteValue();
//            // 向右移8位 
//            temp = temp >> 8; 
//        } 
//        return bytes; 
//    } 
    
    /**
     * 将十六进制字符数组转换为字节数组
     * @param data 十六进制char[]
     * @return byte[]
     * 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     */
//    public static byte[] hex2Bytes(char[] data) {
//        int len = data.length;
//        if ((len & 0x01) != 0) {
//            throw new RuntimeException("Odd number of characters.");
//        }
//        byte[] out = new byte[len >> 1];
//        // two characters form the hex value.
//        for (int i = 0, j = 0; j < len; i++) {
//            int f = toDigit(data[j], j) << 4;
//            j++;
//            f = f | toDigit(data[j], j);
//            j++;
//            out[i] = (byte) (f & 0xFF);
//        }
//        return out;
//    }
	
	/**
	 * 从byte数组指定位置开始读取数字，遇到非数字停止(0x30-0x3B)==0x39
	 * @param src
	 * @param offset
	 * @return 返回ASCII编码字符串
	 */
	public static String readDigitString(byte[] src, int offset) {
		StringBuilder builder = new StringBuilder();
		for (int i = offset; i < src.length; i ++) {
			//if (src[i] < 0x30 || src[i] > 0x3B) {
		    if (src[i] <StreamConstant._0 || src[i] > StreamConstant._9) {
				break;
			}
			builder.append((char)src[i]);
		}
		return builder.toString();
	}
	
	/**
	 * 从byte数组指定位置开始读取，遇到指定值截止
	 * @param src
	 * @param offset
	 * @param delimiter
	 * @return 返回ASCII编码字符串
	 */
	public static String readStringUntil(byte[] src, int offset, byte delimiter) {
		StringBuilder builder = new StringBuilder();
		for (int i = offset; i < src.length; i ++) {
			if (src[i] == delimiter) {
				break;
			}
			builder.append((char)src[i]);
		}
		return builder.toString();
	}
	
	/**
	 * 从byte数组指定位置开始读取，遇到指定值截止
	 * @param src
	 * @param offset
	 * @param delimiter
	 * @return 返回byte[]
	 */
	public static byte[] readByteUntil(byte[] src, int offset, byte delimiter) {
		int endIndex = offset;
		for (; endIndex < src.length; endIndex ++) {
			if (src[endIndex] == delimiter) {
				break;
			}
		}
		int length = endIndex - offset;
		final byte[] subarray = (byte[]) Array
				.newInstance(byte[].class, length);
		System.arraycopy(src, offset, subarray, 0, length);
		return subarray;
	}
	
	/**
	 * 将十六进制字符转换成一个整数
	 * @param ch 十六进制char
	 * @param index 十六进制字符在字符数组中的位置
	 * @return 一个整数
	 *  当ch不是一个合法的十六进制字符时，抛出运行时异常
	 */
	public static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch
					+ " at index " + index);
		}
		return digit;
	}
	
	/**
     * 将上行byte[]数据解包
     */
    public static byte[] unwrap(byte[] data) throws ChecksumException {
        /**
         * 1、转义还原
         */
        byte[] escMsg = null;
        for (int i = 0; i < data.length; i++) {
            byte cur = data[i];
            if (cur == StreamConstant._7D) {
                if (data[i + 1] == 0x01) {
                    escMsg = ArrayUtils.addAll(escMsg, StreamConstant.STREAM_7D);
                } else {
                    escMsg = ArrayUtils.addAll(escMsg, StreamConstant.STREAM_7E);
                }
                i++;
            } else {
                escMsg = ArrayUtils.add(escMsg, cur);
            }
        }

        /**
         * 2、计算校验码
         */
        byte checksum = escMsg[0];
        for (int i = 1; i < escMsg.length - 1; i++) {
            checksum ^= escMsg[i];
        }
        //checksum ^= StreamConstant._7E;
        String checkCodeNeed = StringUtil.toHexStringPadded(
                new byte[]{checksum});
        String checkCodeUpStream = StringUtil.toHexStringPadded(
                new byte[]{escMsg[escMsg.length - 1]});
        if (checksum != escMsg[escMsg.length - 1]) {
            logger.error("通过计算得到的校验码应为:{}",checkCodeNeed);
            logger.error("当前上行数据中的校验码为:{}",checkCodeUpStream);
            throw new ChecksumException("校验码[计算得到的checkCode=" + checkCodeNeed
                    + "]校验[上行数据中的checkCode=" + checkCodeUpStream + "]失败!");
        }else{
            logger.info("通过计算得到的校验码应为:{}",checkCodeNeed);
            logger.info("当前上行数据中的校验码为:{}",checkCodeUpStream);
        }
        return ArrayUtils.subarray(escMsg, 0, escMsg.length - 1);
    }
    
    /**
     * 校验操作
     */
    public static boolean check(byte[] data) throws ChecksumException{
        byte checksum = data[0];
        for (int i = 1; i < data.length - 1; i++) {
            checksum ^= data[i];
        }
        //checksum ^= StreamConstant._7E;
        if (checksum != data[data.length - 1]) {
            throw new ChecksumException();
        }
        return true;
    }
    
    /**
     * 计算校验码
     * byte[] data不包含校验码位
     */
    public static byte calculateCheckCode(byte[] data){
        byte checkCode = data[0];
        for (int i = 1; i < data.length; i++) {
            checkCode ^= data[i];
        }
        return checkCode;
    }
    
    /**
     * 将byte[]数据按照指定的规则进行装包
     * @param origin:不包含标识符7E和校验码
     * @return wrap:包含标识符7E和校验码
     */
    public static byte[] wrap(byte[] origin) {
        /**
         * 1、计算校验码
         */
        byte checksum = origin[0];
        //checksum ^= StreamConstant._7E;
        for (int i = 1; i < origin.length; i++) {
            checksum ^= origin[i];
        }
        //checksum ^= StreamConstant._7E;
        origin = ArrayUtils.addAll(origin, checksum);
        /**
         * 2、转义
         */
        byte[] wrap = StreamConstant.STREAM_7E; 
        //byte[] wrap = new byte[]{}; 
        for (int i = 0; i < origin.length; i++) {
            byte cur = origin[i];
            if (cur == StreamConstant._7D) {
                wrap = ArrayUtils.addAll(wrap, StreamConstant._7D, StreamConstant._01);
            } else if  (cur == StreamConstant._7E) {
                wrap = ArrayUtils.addAll(wrap, StreamConstant._7D, StreamConstant._02);
            } else {
                wrap = ArrayUtils.add(wrap, cur);
            }
        }
        wrap = ArrayUtils.add(wrap, StreamConstant._7E);
        return wrap;
    }
    
    /**
     * toHexString
     * 将String数据按照指定的规则进行装包,转换为HEX字符串
     */
    public static String wrap(String data) {
        return StringUtil.toHexStringPadded(wrap(data.getBytes()));
    }
    
    /**
     * 从低位开始计数
     * @param index 从0开始
     * @param target 0或1
     * @return
     */
    public static boolean trueFalseFromBit(byte b, int index, int target) {
        return ((b >> index) & 0x01) == target;
    }
    
    /**
     * 从低位开始计数
     * @param i
     * @param index 从0开始
     * @param target 0或1
     * @return
     */
    public static boolean trueFalseFromBit(int i, int index, int target) {
        return ((i >> index) & 0x01) == target;
    }
    
    /**
     * 从低位开始计数
     * @param l
     * @param index 从0开始
     * @param target 0或1
     * @return
     */
    public static boolean trueFalseFromBit(long l, int index, int target) {
        return ((l >> index) & 0x01) == target;
    }
    
    /**
     * hexString转为GBK格式的String字符串
     * 例如:000102030405===>>String
     */
    public static String hexString2GBKString(String hexString) { 
        return bytes2GBKString(hexString2Bytes(hexString)); 
    }
    /**
     * byte[] 数组转为GBK格式的String字符串
     * 例如:byte[]{0x00,0x01,0x02,0x03,0x04,0x05}===>>String
     */
    private static String bytes2GBKString(byte[] bytes) { 
        return new String(bytes, Charset.forName("GBK")); 
    }
    
    /**
     * byte[] 转为HEX格式的String字符串
     * 例如:byte[]{0x00,0x01,0x02,0x03,0x04,0x05}===>>String{000102030405}
     */
    @Deprecated
    public static String bytes2HexString(byte[] b) { 
        String ret = ""; 
        for (int i = 0; i < b.length; i++) { 
            String hex = Integer.toHexString(b[i] & 0xFF); 
            if (hex.length() == 1) { 
                hex = '0' + hex; 
            } 
            ret += hex.toUpperCase(); 
        } 
        return ret; 
    }
    
    /**
     * byte[] 转为HEX格式的String字符串
     * 例如:byte[]{0x00,0x01,0x02,0x03,0x04,0x05}===>>String{000102030405}
     */
    @Deprecated
    public static String bytes2HexString(byte[] b,int startIndex,int length) { 
        if(b.length<=startIndex
                ||b.length<length
                ||b.length<(startIndex+length)
                ){
            return null;
        }
        String ret = ""; 
        for (int i = startIndex; i < startIndex+length; i++) { 
            String hex = Integer.toHexString(b[i] & 0xFF); 
            if (hex.length() == 1) { 
                hex = '0' + hex; 
            } 
            ret += hex.toUpperCase(); 
        } 
        return ret; 
    }
    
    @Deprecated
    public static String bytes2HexString(byte[] b,int length) { 
        return bytes2HexString(b, 0, length); 
    }
    
}
