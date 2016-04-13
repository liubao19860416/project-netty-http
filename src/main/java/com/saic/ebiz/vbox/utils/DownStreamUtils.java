package com.saic.ebiz.vbox.utils;

import io.netty.util.internal.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.exception.ChecksumException;
import com.saic.ebiz.vbox.stream.base.DownStream;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.down.CarStatusDownStream;
import com.saic.ebiz.vbox.stream.down.CommonDownStream;
import com.saic.ebiz.vbox.stream.down.EmptyMsgBodyDownStream;
import com.saic.ebiz.vbox.stream.down.LoginDownStream;

/**
 * Lite+协议解析工具类:针对下行数据Stream类型封装的工具类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月27日
 *
 */
public class DownStreamUtils {
    
    private static Logger logger = LoggerFactory.getLogger(DownStreamUtils.class);
    
    /**
     * 填充LoginDownStream实体信息
     * @param loginDownStream
     * @param upStream
     * @param responseNumber
     * @param responseId
     * @param responseResult
     * @return
     */
    public static LoginDownStream fillInfoForLoginDownStream(
            LoginDownStream loginDownStream, UpStream upStream,
            String responseNumber, String responseId, String responseResult) {
        logger.info("填充LoginDownStream实体信息开始.");
        //填充基础数据
        fillPrefixCommandFullByMsgProperty(loginDownStream, upStream.getSn(),upStream.getMsgNumber(),loginDownStream.getMsgProperty());
        
        String commandFull = loginDownStream.getCommandFull();
        StringBuilder sb1=new StringBuilder();
        sb1.append(commandFull)
            .append(responseNumber)
            .append(responseId)
            .append(responseResult);
        byte[] hexString2Bytes = ByteUtil.hexString2Bytes(sb1.toString());
        //封包添加校验码
        byte[] wrap = ByteUtil.wrap(hexString2Bytes);
        logger.info("校验下行wrap的HEX格式数据为:{}",StringUtil.toHexStringPadded(wrap));
        
        byte checkCodeByte = wrap[wrap.length-2];
        String checkCode = StreamUtils.formatCheckCode(StringUtil.byteToHexString(checkCodeByte));
        logger.info("校验下行校验码HEX格式数据为:{}:",checkCode);
        
        //校验测试,需要去除首尾的标识符
        try {
            logger.info("校验下行数据的结果为:{}:",
                    ByteUtil.check(ArrayUtils.subarray(wrap, 1, wrap.length-1)));
        } catch (ChecksumException e) {
            logger.error("校验下行数据的结果异常...",e);
        }
        
        StringBuilder sb2=new StringBuilder();
        commandFull=sb2.append(StreamConstant.MARK_BIT)
                .append(sb1)
                .append(checkCode)
                .append(StreamConstant.MARK_BIT)
                .toString();
        loginDownStream.setCheckCode(checkCode);
        loginDownStream.setCommandFull(commandFull);
        
        byte[] dataStream = ByteUtil.hexString2Bytes(commandFull);
        loginDownStream.setDataStream(dataStream);
        logger.info("填充LoginDownStream实体信息结束.");
        return loginDownStream;
    }

    /**
     * 填充CommonDownStream实体信息
     * @param commonDownStream
     * @param upStream
     * @param responseResult 
     * @param responseId 
     * @param responseNumber 
     * @return
     */
    public static CommonDownStream fillInfoForCommonDownStream(
            CommonDownStream commonDownStream, UpStream upStream, String responseNumber, String responseId, String responseResult) {
        logger.info("填充CommonDownStream实体信息开始.");
        //填充基础数据
        fillPrefixCommandFullByMsgProperty(commonDownStream, upStream.getSn(),upStream.getMsgNumber(),commonDownStream.getMsgProperty());
        
        String commandFull = commonDownStream.getCommandFull();
        StringBuilder sb1=new StringBuilder();
        sb1.append(commandFull)
        .append(responseNumber)
        .append(responseId)
        .append(responseResult);
        byte[] hexString2Bytes = ByteUtil.hexString2Bytes(sb1.toString());
        //封包添加校验码
        byte[] wrap = ByteUtil.wrap(hexString2Bytes);
        logger.info("校验下行wrap的HEX格式数据为:{}",StringUtil.toHexStringPadded(wrap));
        
        byte checkCodeByte = wrap[wrap.length-2];
        String checkCode = StreamUtils.formatCheckCode(StringUtil.byteToHexString(checkCodeByte));
        logger.info("校验下行校验码HEX格式数据为:{}:",checkCode);
        
        //校验测试,需要去除首尾的标识符
        try {
            logger.info("校验下行数据的结果为:{}:",
                    ByteUtil.check(ArrayUtils.subarray(wrap, 1, wrap.length-1)));
        } catch (ChecksumException e) {
            logger.error("校验下行数据的结果异常...",e);
        }
        
        StringBuilder sb2=new StringBuilder();
        commandFull=sb2.append(StreamConstant.MARK_BIT)
                .append(sb1)
                .append(checkCode)
                .append(StreamConstant.MARK_BIT)
                .toString();
        commonDownStream.setCheckCode(checkCode);
        commonDownStream.setCommandFull(commandFull);
        
        byte[] dataStream = ByteUtil.hexString2Bytes(commandFull);
        commonDownStream.setDataStream(dataStream);
        logger.info("填充CommonDownStream实体信息结束.");
        return commonDownStream;
    }
    
    /**
     * 填充EmptyMsgBodyDownStream实体信息
     * @param emptyMsgBodyDownStream
     * @return
     */
    public static EmptyMsgBodyDownStream fillInfoForEmptyMsgBodyDownStream(
            EmptyMsgBodyDownStream emptyMsgBodyDownStream) {
        logger.info("填充EmptyMsgBodyDownStream实体信息开始.");
        //填充基础数据
        fillPrefixCommandFullByMsgProperty(emptyMsgBodyDownStream, 
                emptyMsgBodyDownStream.getSn(),emptyMsgBodyDownStream.getMsgNumber(),emptyMsgBodyDownStream.getMsgProperty());
        
        String commandFull = emptyMsgBodyDownStream.getCommandFull();
        StringBuilder sb1=new StringBuilder();
        sb1.append(commandFull);
        byte[] hexString2Bytes = ByteUtil.hexString2Bytes(sb1.toString());
        //封包添加校验码
        byte[] wrap = ByteUtil.wrap(hexString2Bytes);
        logger.info("校验下行wrap的HEX格式数据为:{}",StringUtil.toHexStringPadded(wrap));
        
        byte checkCodeByte = wrap[wrap.length-2];
        String checkCode = StreamUtils.formatCheckCode(StringUtil.byteToHexString(checkCodeByte));
        logger.info("校验下行校验码HEX格式数据为:{}:",checkCode);
        
        //校验测试,需要去除首尾的标识符
        try {
            logger.info("校验下行数据的结果为:{}:",
                    ByteUtil.check(ArrayUtils.subarray(wrap, 1, wrap.length-1)));
        } catch (ChecksumException e) {
            logger.error("校验下行数据的结果异常...",e);
        }
        
        StringBuilder sb2=new StringBuilder();
        commandFull=sb2.append(StreamConstant.MARK_BIT)
                .append(sb1)
                .append(checkCode)
                .append(StreamConstant.MARK_BIT)
                .toString();
        emptyMsgBodyDownStream.setCheckCode(checkCode);
        emptyMsgBodyDownStream.setCommandFull(commandFull);
        
        byte[] dataStream = ByteUtil.hexString2Bytes(commandFull);
        emptyMsgBodyDownStream.setDataStream(dataStream);
        logger.info("填充EmptyMsgBodyDownStream实体信息结束.{}",emptyMsgBodyDownStream.toString());
        return emptyMsgBodyDownStream;
    }
    
    /**
     * 填充CarStatusDownStream实体信息  
     * @param carStatusDownStream
     * @return
     */
    public static CarStatusDownStream fillInfoForCarStatusDownStream(
            CarStatusDownStream carStatusDownStream) {
        logger.info("填充EmptyMsgBodyDownStream实体信息开始.");
        //填充基础数据
        fillPrefixCommandFullByMsgProperty(carStatusDownStream, 
                carStatusDownStream.getSn(),carStatusDownStream.getMsgNumber(),carStatusDownStream.getMsgProperty());
        
        String commandFull = carStatusDownStream.getCommandFull();
        StringBuilder sb1=new StringBuilder();
        sb1.append(commandFull);
        //添加请求参数信息
        sb1.append(carStatusDownStream.getCarCheckType().getKey());
        
        byte[] hexString2Bytes = ByteUtil.hexString2Bytes(sb1.toString());
        //封包添加校验码
        byte[] wrap = ByteUtil.wrap(hexString2Bytes);
        logger.info("校验下行wrap的HEX格式数据为:{}",StringUtil.toHexStringPadded(wrap));
        
        byte checkCodeByte = wrap[wrap.length-2];
        String checkCode = StreamUtils.formatCheckCode(StringUtil.byteToHexString(checkCodeByte));
        logger.info("校验下行校验码HEX格式数据为:{}:",checkCode);
        
        //校验测试,需要去除首尾的标识符
        try {
            logger.info("校验下行数据的结果为:{}:",
                    ByteUtil.check(ArrayUtils.subarray(wrap, 1, wrap.length-1)));
        } catch (ChecksumException e) {
            logger.error("校验下行数据的结果异常...",e);
        }
        
        StringBuilder sb2=new StringBuilder();
        commandFull=sb2.append(StreamConstant.MARK_BIT)
                .append(sb1)
                .append(checkCode)
                .append(StreamConstant.MARK_BIT)
                .toString();
        carStatusDownStream.setCheckCode(checkCode);
        carStatusDownStream.setCommandFull(commandFull);
        
        byte[] dataStream = ByteUtil.hexString2Bytes(commandFull);
        carStatusDownStream.setDataStream(dataStream);
        logger.info("填充EmptyMsgBodyDownStream实体信息结束.{}",carStatusDownStream.toString());
        return carStatusDownStream;
    }

    /**
     * 将上行数据实体信息,构建基础前缀CommandFull数据到下行数据CommonDownStream实体信息中
     * DownStream中的消息体MsgProperty长度为0/5/10,故MsgProperty为0000/0005/000A;
     * 
     * @param DownStream dest
     */
    private static void fillPrefixCommandFullByMsgProperty(DownStream dest,String sn,String msgNumber,String msgProperty) {
        if(dest==null
                ||StringUtils.isBlank(sn)
                ||StringUtils.isBlank(msgNumber)){
            return ;
        }
        if(StringUtils.isBlank(msgNumber)){
            msgNumber="";
        }
        if(StringUtils.isBlank(msgProperty)){
            msgProperty="";
        }
        StringBuilder sb=new StringBuilder();
        sb.append(dest.getCommandType())
            .append(msgProperty)
            .append(sn)
            .append(msgNumber);
        dest.setCommandFull(sb.toString());
    }
    
}
