package com.saic.ebiz.vbox.mq;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;

import com.alibaba.fastjson.JSON;
import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.stream.base.DownStream;
import com.saic.ebiz.vbox.stream.bean.CarCheckType;
import com.saic.ebiz.vbox.utils.ChannelUtil;
import com.saic.ebiz.vbox.utils.MyStringUtil;
import com.saic.ebiz.vbox.utils.StreamFactory;

/**
 * MQ消息接收监听程序
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年11月10日
 * 
 */
@SuppressWarnings("deprecation")
public class QueueMessageListener implements MessageListener {
    
    private static Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);
    private StreamMessageConverter messageConverter;

    @Override
    public void onMessage(Message message) {
        try {
            logger.warn("MQ消息接收ActiveMQMessage监听程序Received: JMSMessageID={}", message.getJMSMessageID());
            if (!(message instanceof ActiveMQMessage)) {
                logger.error("暂不支持的数据类型,MQ消息接收的Message数据类型不是ActiveMQMessage: {}",message);
            }
            Object fromMessage = messageConverter.fromMessage(message);
            //处理消息
            processMessage(fromMessage);
        } catch (MessageConversionException | JMSException e) {
            logger.error("转换MQ数据异常",e);
        }
            
    }
    
    /**
     * 接收到消息后,进行对应的MQ消息处理
     */
    @SuppressWarnings("rawtypes")
    private void processMessage(Object fromMessage) {
        if(fromMessage==null){
            return;
        }
        //数据定义
        String commandType = "";
        String sn = "";
        DownStream downStream =null;
        
        if(fromMessage instanceof String){
            String textMessage = (String)fromMessage;
            logger.warn("MQ消息接收textMessage监听程序Received: {}", JSON.toJSONString(textMessage));
            //手动触发MQ测试下行数据发送,格式为:commandType:sn ===>>> 8107:08201060400001
            if(StringUtils.isNotBlank(textMessage)){
                //读取commandType码和sn
                String[] split = textMessage.split(":");
                if(split==null||split.length!=2){
                    logger.error("数据格式不正确,不做任何处理!");
                    return ;
                }
                commandType = split[0];
                sn = split[1];
            }
        }else if(fromMessage instanceof Map){
            //真实的运行环境,使用该类型传递数据  TODO 
            Map mapMessage = (Map)fromMessage;
            logger.warn("MQ消息接收mapMessage监听程序Received: {}", JSON.toJSONString(mapMessage));
            // 添加解析数据操作 TODO
            commandType = MyStringUtil.getStringValue(mapMessage.get("commandType"));
            sn = MyStringUtil.getStringValue(mapMessage.get("sn"));
            @SuppressWarnings("unused")
            String userId = MyStringUtil.getStringValue(mapMessage.get("userId"));
        }else{
            logger.error("MQ消息接收监听程序Received不是Map/String信息: {}", JSON.toJSONString(fromMessage));
        }
        
        //构建下行实体数据信息
        switch (commandType) {
        case StreamConstant.COMMAND_TYPE_8201:
        case StreamConstant.COMMAND_TYPE_8107:
        case StreamConstant.COMMAND_TYPE_8203:
            logger.info("构建无请求参数下行数据实体!");
            //downStream = StreamFactory.buildDownStreamForCommandType_8107(sn);
            downStream = StreamFactory.buildDownStreamForCommandType(commandType, sn, new Object[]{});
            break;
            //带参数的下行实体构建
        case StreamConstant.COMMAND_TYPE_8B00:
            //TODO ,暂时使用默认测试数据
            String carCheckType=CarCheckType.ERRORCODE.getKey();
            downStream = StreamFactory.buildDownStreamForCommandType(commandType, sn, carCheckType);
            break;
            
        default: {
            logger.info("不存在对应的[commandType={},sn={}]信息,构建下行实体数据信息失败!",commandType,sn);
        }
        }
        
        //发送下行数据到对应的Channel TODO
        Channel channel = ChannelUtil.get(sn);
        try {
            if(channel!=null&&downStream!=null
                    &&StringUtils.isNotBlank(sn)
                    &&StringUtils.isNotBlank(commandType)){
                @SuppressWarnings("unused")
                ChannelFuture future=null;
                logger.info("将下行数据sn添加到管道流中成功[commandType={},sn={}]!",commandType,sn);
                future = channel.writeAndFlush(downStream).sync();
                //future = channel.writeAndFlush(downStream);
            }else{
                logger.warn("未将下行数据[commandType={},sn={}]添加到管道流中,Channel为空!",commandType,sn);
            }
        } catch (InterruptedException e) {
            logger.error("将下行数据[commandType={},sn={}]添加到管道流中执行异常!{}",commandType,sn);
        }
        
    }

    public void setMessageConverter(StreamMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
  
}