package com.saic.ebiz.vbox.mq;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.CommandTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.alibaba.fastjson.JSON;

/**
 * 上行数据消息转换器
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年11月10日
 * 
 */
public class StreamMessageConverter implements MessageConverter {
    private static Logger logger = LoggerFactory.getLogger(StreamMessageConverter.class);
    
    private long expiratedTime;

    /**
     * 转换接收到的Message消息对象为Object对象,接收MQ中的消息数据
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object fromMessage(Message message) throws JMSException,MessageConversionException {
        if (logger.isDebugEnabled()) {
            logger.debug("转换接收到的Message消息对象为Object对象,Receive JMS message :" + message);
        }
        if (message instanceof ActiveMQMessage) {
            try {
                ActiveMQMessage objectMessage = (ActiveMQMessage)message;
                byte dataStructureType = objectMessage.getDataStructureType();
                logger.debug("MQ数据消息转换器 CommandTypes.ACTIVEMQ_TYPE_MESSAGE={}", dataStructureType);
                
                if(CommandTypes.ACTIVEMQ_MAP_MESSAGE==dataStructureType){
                    //TODO 
                    ActiveMQMapMessage mapMessage = (ActiveMQMapMessage) message;
                    //Map<String, Object> contentMap = mapMessage.getContentMap();
                    //logger.info("MQ数据转换器,转换ActiveMQMapMessage.getContentMap结果为{}", JSON.toJSONString(contentMap));
                    Map properties = mapMessage.getProperties();
                    logger.debug("MQ数据转换器,转换ActiveMQMapMessage.getProperties结果为{}", JSON.toJSONString(properties));
                    return properties;
                }else if(CommandTypes.ACTIVEMQ_TEXT_MESSAGE==dataStructureType){
                    ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
                    String text = textMessage.getText();
                    logger.debug("MQ数据转换器,转换ActiveMQTextMessage结果为{}", JSON.toJSONString(text));
                    return text;
                }else if(CommandTypes.ACTIVEMQ_OBJECT_MESSAGE==dataStructureType){
                    ActiveMQObjectMessage aMsg = (ActiveMQObjectMessage) message;
                    //Serializable object = aMsg.getObject();
                    logger.error("MQ数据转换器,转换ActiveMQObjectMessage结果为{}", JSON.toJSONString(aMsg));
                    logger.error("Message:${} is not a instance of UpStreamBaseBean."+ message.toString());
                    //throw new JMSException("Message:" + object.toString()+ "is not a instance of UpStreamBaseBean or DownStreamBaseBean.");
                }else{
                    logger.error("不支持的类型转换."+ message.toString());
                    //throw new JMSException("Message:" + message.toString()+ "不支持的类型转换.");
                }
            } catch (Exception e) {
                logger.error("Message:${} is not a instance of ActiveMQMessage."+ message.toString());
                //throw new JMSException("Message:" + message.toString()+ "is not a instance of ActiveMQMessage.");
            }
        } else {
            logger.error("Message:${} is not a instance of ActiveMQMessage."+ message.toString());
            throw new JMSException("Message:" + message.toString()+ "is not a instance of ActiveMQMessage.");
        }
        return null;
    }

    /**
     * 转换Object对象到Message消息对象,发送消息到MQ
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Message toMessage(Object obj, Session session) throws JMSException,
            MessageConversionException {
        if (logger.isDebugEnabled()) {
            logger.debug("转换Object对象到Message消息对象,Convert to JMS message:${}", obj.toString());
        }
        ActiveMQMessage msg0=null;
        if (obj instanceof Map) {
            ActiveMQMapMessage msg = (ActiveMQMapMessage) session.createMapMessage();
            msg.setJMSExpiration(expiratedTime);   
            msg.setProperties((Map)obj);
            msg0= msg;
        }else if (obj instanceof String) {
            ActiveMQTextMessage msg = (ActiveMQTextMessage) session.createTextMessage();
            msg.setJMSExpiration(expiratedTime);   
            msg.setText((String)obj);
            msg0= msg;
        }else{
            logger.error("转换Object对象到Message消息对象Convert object to JMS message:${}", obj.toString());
            //throw new JMSException("Message:" + obj.toString()+ "is not a instance of ActiveMQObjectMessage.");
        }
        return msg0;
    }

    public void setExpiratedTime(long expiratedTime) {
        this.expiratedTime = expiratedTime;
    }

}