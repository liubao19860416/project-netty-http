package com.saic.ebiz.vbox.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理连接超时，关闭连接
 *
 */
@Sharable
@Component
public class GprsIdleStateHandler extends ChannelDuplexHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GprsIdleStateHandler.class);
	
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
            	logger.info("设备连接超时");
            	//当长时间接收不到设备消息，关闭连接
                ctx.disconnect();
            }
        }
    }
}