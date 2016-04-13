package com.saic.ebiz.vbox.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.StringUtil;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.saic.ebiz.vbox.stream.base.DownStream;

/**
 * 将下行数据写入到Channel
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月21日
 * 
 */
@Sharable
@Component
public class StreamToByteEncoder extends MessageToMessageEncoder<DownStream> {

    private static final Logger logger = LoggerFactory.getLogger(StreamToByteEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, DownStream downStream,
            List<Object> out) throws Exception {
        logger.info("服务器接收到要发送的下行DownStream:{}", JSON.toJSONString(downStream));
        
        String commandType = downStream.getCommandType();
        logger.info("服务器接收到要发送的下行指令commandType={},HEX数据:{}",commandType, 
                StringUtil.toHexStringPadded(downStream.getDataStream()));

        out.add(Unpooled.wrappedBuffer(downStream.getDataStream()));
        out.add(downStream);
        
        logger.info("服务器发送下行DownStream结束.");
    }

}
