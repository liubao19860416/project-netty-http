package com.saic.ebiz.vbox.utils;

import io.netty.util.internal.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.exception.ChecksumException;
import com.saic.ebiz.vbox.exception.CommandTypeNotFoundException;
import com.saic.ebiz.vbox.exception.NullRequestParamException;
import com.saic.ebiz.vbox.exception.ParseStreamException;
import com.saic.ebiz.vbox.handler.ByteToStreamDecoder;
import com.saic.ebiz.vbox.stream.base.DownStream;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.stream.bean.CarCheckType;
import com.saic.ebiz.vbox.stream.down.CarStatusDownStream;
import com.saic.ebiz.vbox.stream.down.CommonDownStream;
import com.saic.ebiz.vbox.stream.down.EmptyMsgBodyDownStream;
import com.saic.ebiz.vbox.stream.down.LoginDownStream;

/**
 * 上行/下行数据流解析工具类
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 * 
 */
public class StreamFactory {

    private static final Logger logger = LoggerFactory.getLogger(ByteToStreamDecoder.class);
    
    private StreamFactory() {
    }

    /**
     * 构建上行数据对象
     * 
     * @param dataStream
     * @return UpStream
     * @throws ChecksumException
     */
    public static UpStream buildUpStream(byte[] dataStream) throws ChecksumException {
        logger.info("构建上行数据对象开始...");
        //1.解包
        dataStream = ByteUtil.unwrap(dataStream);
        //2.获取消息类型
        String hexStringPadded = StringUtil.toHexStringPadded(dataStream);
        logger.info("解包后得到的HEX数据:{}", hexStringPadded);
        String commandType = StreamUtils.parseCommandType(dataStream);
        logger.info("解析到命令头：{}", commandType);
        //3.根据消息类型,进行数据对应的解析
        switch (commandType) {
        //上行指令
        case StreamConstant.COMMAND_TYPE_0001:
            return StreamUtils.parseCommonUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0200:
            return StreamUtils.parseTimingUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0206:
            return StreamUtils.parseBasicInfoUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0208:
            return StreamUtils.parseCanDataUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0214:
            return StreamUtils.parseCarStatusUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0201:
            return StreamUtils.parseAlarmUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0209:
            return StreamUtils.parseErrorCodeUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0220:
            return StreamUtils.parseOneKeyDetectionUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0221:
            return StreamUtils.parseFullCarScanUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0205:
            return StreamUtils.parseTripUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0215:
            return StreamUtils.parsePointCompensateUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0104:
            return StreamUtils.parseQueryParametersUpStream(dataStream);
        case StreamConstant.COMMAND_TYPE_0202:
            return StreamUtils.parseQueryLocationUpStream(dataStream);
            
            
        case StreamConstant.COMMAND_TYPE_0102:
        case StreamConstant.COMMAND_TYPE_0002:
        case StreamConstant.COMMAND_TYPE_0212:
        case StreamConstant.COMMAND_TYPE_0213:
            return StreamUtils.parseNoMsgBodyUpStream(commandType,dataStream);
            
        default:
            logger.error("不存在的当前命令类型[{}],未构建上行数据对象,当前请求数据为:{}",commandType,hexStringPadded);
            return null;
            //throw new ParseStreamException("buildUpStream解析上行数据失败，找不到对应的数据类型!");
        }
    }
    
    /**
     * 根据上行数据对象,自动构建对应的下行数据对象
     * 
     * @param dataStream
     * @return
     * @throws ChecksumException
     */
    public static DownStream buildDownStreamByUpStream(UpStream upStream) throws ChecksumException {
        logger.info("根据上行数据对象,自动构建对应的下行数据对象,上行数据UpStream为:" + JSON.toJSONString(upStream));
        String commantType = upStream.getCommandType();
        String msgProperty =null;
        String sn = upStream.getSn();
        //对应设备消息的流水号
        String msgNumber = upStream.getMsgNumber();
        
        //获取上行指令存在需要自动回复的下行指令信息
        String downCommandType = StreamConstant.upMappingDownRulesMap.get(commantType);
        if(StringUtils.isNotBlank(downCommandType)){
            //不为空,说明存在上行指令存在需要自动回复的下行指令信息
            logger.info("存在上行指令存在需要自动回复的下行指令信息,原始上行commantType={}信息,匹配到对应的下行数据downCommandType={}",commantType,downCommandType);
            //根据消息类型,进行下行数据实体的封装
            switch (downCommandType) {
            //下行指令
            case StreamConstant.COMMAND_TYPE_8001:
                msgProperty = StreamConstant.MSGPROPERTY_0005;
                String responseNumber=msgNumber;
                String responseId=commantType;
                String responseResult=StreamConstant.OK_RESPONSERESULT;
                String msgBody = responseNumber+responseId+responseResult;
                logger.info("拼装得到的下行结果msgBody={},responseResult={}.",msgBody,responseResult);
                
                CommonDownStream commonDownStream = new CommonDownStream(
                        downCommandType,msgProperty,sn,msgNumber,msgBody,responseNumber+"_S",responseId,responseResult);
                return DownStreamUtils.fillInfoForCommonDownStream(commonDownStream,upStream,responseNumber,responseId,responseResult);
                
            case StreamConstant.COMMAND_TYPE_8102:
                msgProperty = StreamConstant.MSGPROPERTY_000A;
                responseNumber=msgNumber;
                responseId=commantType;
                String formatGMT0Time = GMTDateUtil.formatGMT0Time();
                responseResult=formatGMT0Time.replaceAll(GMTDateUtil.GMT_DATE_SEPERATOR, "");
                msgBody = responseNumber+responseId+responseResult;
                logger.info("拼装得到的下行结果msgBody={},responseResult={}.",msgBody,responseResult);
                
                LoginDownStream loginDownStream = new LoginDownStream(
                        downCommandType,msgProperty,sn,msgNumber,msgBody,responseNumber+"_S",responseId,responseResult);
                return DownStreamUtils.fillInfoForLoginDownStream(loginDownStream,upStream,responseNumber,responseId,responseResult);
                
                
            default:
                throw new ParseStreamException("根据上行数据对象,自动构建对应的下行数据对象，找不到对应的数据类型!");
            }
        }else{
            //不存在需要自动回复的下行指令信息
            logger.info("上行指令commantType={}不存在需要自动回复的下行指令信息.",commantType);
            return null;
        }
        
    }
    
    /**
     * 无参数构建下行实体信息
     * @param commandType
     * @param sn
     * @return
     */
    public static DownStream buildDownStreamForCommandType_8201(String sn) {
        return buildDownStreamForCommandType(StreamConstant.COMMAND_TYPE_8201, sn, new Object[]{});
    }
    
    public static DownStream buildDownStreamForCommandType_8107(String sn) {
        return buildDownStreamForCommandType(StreamConstant.COMMAND_TYPE_8107, sn, new Object[]{});
    }
    
    public static DownStream buildDownStreamForCommandType_8203(String sn) {
        return buildDownStreamForCommandType(StreamConstant.COMMAND_TYPE_8203, sn, new Object[]{});
    }
    
    /**
     * 泛型多请求参数实现,构建下行数据实体
     * @param commandKey
     * @param sn
     * @param Object... obj
     */
    public static DownStream buildDownStreamForCommandType(String commandType,String sn,Object... obj) {
        logger.info("下行指令,根据不定长请求参数[sn={},commandKey={}]方法,封装下行数据实体开始执行!!!",sn,commandType);
        String msgNumber=StreamConstant.MSGNUMBER_DEFAULT;
        if (obj == null||obj.length<=0) {
            //不带请求参数的下行数据实体封装
            EmptyMsgBodyDownStream emptyMsgBodyDownStream = null;
            String msgProperty=StreamConstant.MSGPROPERTY_0000;
            String msgBody=StreamConstant.EMPTY;
            switch (commandType) {
            case StreamConstant.COMMAND_TYPE_8201:
            case StreamConstant.COMMAND_TYPE_8107:
            case StreamConstant.COMMAND_TYPE_8203:
                logger.info("请求参数Object... obj为空.调用通用的方法,解析构建下行数据实体!");
                emptyMsgBodyDownStream = new EmptyMsgBodyDownStream(commandType, msgProperty, sn, msgNumber, msgBody);        
                break;
            default: {
                logger.info("请求参数Object... obj为空!");
                throw new NullRequestParamException("解析构建下行数据实体[commandType="+commandType+",sn="+sn+"],请求参数Object... obj为空!请调用对应的特殊方法或者添加请求参数!");
                //break;
            }
            }
            //填充数据
            return DownStreamUtils.fillInfoForEmptyMsgBodyDownStream(emptyMsgBodyDownStream);
        }else{
            //带请求参数的下行数据实体封装
            StringBuilder sb=new StringBuilder();
            for(Object object : obj) {
                sb.append(String.valueOf(object));
            }
            logger.warn("拼装的请求参数结果为:{}",sb.toString());
            
            //开始封装实体
            switch (commandType) {
            case StreamConstant.COMMAND_TYPE_8B00:
                logger.info("解析构建下行数据实体,请求参数[{}]不为空!",sb.toString());
                String carCheckType = sb.toString();
                String msgProperty=StreamConstant.MSGPROPERTY_0001;
                String msgBody=carCheckType;
                CarStatusDownStream carStatusDownStream=
                        new CarStatusDownStream(commandType, msgProperty, sn, msgNumber, msgBody, CarCheckType.getByKey(carCheckType));
                
                //补全其他commandFull/dataStream/checkCode信息 TODO 
                return DownStreamUtils.fillInfoForCarStatusDownStream(carStatusDownStream);
                
                //TODO 其他类型的下行数据封装
                
                
            default: {
                logger.info("请求参数[{}]不为空!",sb.toString());
                throw new CommandTypeNotFoundException("解析构建下行数据实体[commandType="+commandType+",sn="+sn+"],请求参数Object... obj不为空!");
                //break;
            }
            }
            //return null;
        }
        
    }

    
}
