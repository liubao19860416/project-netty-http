package com.saic.ebiz.vbox.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * 点对点消息生产者(发送消息)
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年11月10日
 * 
 */
public class QueueMessageSender {
    private final Logger logger = LoggerFactory.getLogger(QueueMessageSender.class);
    
    private long receiveTimeout;
    private JmsTemplate jmsTemplate;
    private StreamMessageConverter messageConverter;

    public void sendQueue(Object object) {
        sendStreamBaseBeanMessage(object);
    }

    private void sendStreamBaseBeanMessage(Object object) {
        jmsTemplate.setMessageConverter(messageConverter);
//        jmsTemplate.setPubSubDomain(false);
//        jmsTemplate.convertAndSend(destination, upStreamBaseBean);
        jmsTemplate.setReceiveTimeout(receiveTimeout);
        jmsTemplate.convertAndSend(object);
        logger.warn("发送Object消息到MQ消息执行结束了...");
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    
    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public void setMessageConverter(StreamMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

}