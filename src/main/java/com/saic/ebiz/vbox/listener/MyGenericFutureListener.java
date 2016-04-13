package com.saic.ebiz.vbox.listener;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义的监听器回调实现
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年4月10日
 * 
 */
public class MyGenericFutureListener implements
        GenericFutureListener<Future<?>> {
    
    private static final Logger logger = LoggerFactory.getLogger(MyGenericFutureListener.class);
 
    /**
     * 在该方法实现对应的回调逻辑
     */
    @Override
    public void operationComplete(Future<?> future) throws Exception {
        logger.warn("回调结果future.isSuccess():{}",future.isSuccess());
        logger.warn("回调结果future.isCancelled():{}",future.isCancelled());
        logger.warn("回调结果future.isCancelled():{}",future.isCancelled());
        logger.warn("回调结果future.isDone():{}",future.isDone());
    }

}
