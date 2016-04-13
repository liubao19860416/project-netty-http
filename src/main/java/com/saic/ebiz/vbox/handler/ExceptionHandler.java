package com.saic.ebiz.vbox.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.saic.ebiz.vbox.exception.ChecksumException;
import com.saic.ebiz.vbox.exception.MongoSaveException;
import com.saic.ebiz.vbox.exception.ParsePidException;
import com.saic.ebiz.vbox.exception.ParseStreamException;

/**
 * 处理Channel读写时发生的异常
 * @author hechao
 *
 */
@Sharable
@Component
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
		
		if (cause instanceof ParseStreamException) {
			LOGGER.error("解析上行数据流出错", cause);
		} else if (cause instanceof ChecksumException) {
			LOGGER.error("上行数据校验失败", cause);
		} else if (cause instanceof MongoSaveException) {
			LOGGER.error("保存数据到MongoDB失败", cause);
		} else if (cause instanceof ParsePidException) {
			LOGGER.error("解析PID失败", cause);
		} else {
			LOGGER.error("未知错误", cause);
		}
		
    }
}
