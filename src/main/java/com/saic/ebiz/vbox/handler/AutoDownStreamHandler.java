package com.saic.ebiz.vbox.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.stream.base.DownStream;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.utils.StreamFactory;

/**
 * 根据上行数据实体,构造对应的下行数据,返回客户端
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月21日
 *
 */
@Sharable
@Component
public class AutoDownStreamHandler extends SimpleChannelInboundHandler<UpStream> {
    private static final Logger logger = LoggerFactory.getLogger(AutoDownStreamHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,UpStream upStream) throws Exception {
        logger.info("根据上行数据实体,构造对应的下行数据,返回客户端.");
        try {
            //获取对应下行数据流实体信息
            DownStream downStream = StreamFactory.buildDownStreamByUpStream(upStream);
            logger.info("根据上行数据实体,构造得到的DownStream为:"+JSON.toJSONString(downStream));
            
            //添加校验条件
            if(downStream!=null
                    &&downStream.getDataStream()!=null
                    &&downStream.getDataStream().length>=StreamConstant.MIN_LENGTH
                    &&StringUtils.isNotBlank(downStream.getCommandFull())
                    ){
                logger.warn("解析得到的DownStream符合条件,添加到管道流中!");
                ctx.writeAndFlush(downStream);
            }else{
                logger.warn("解析得到的DownStream不符合条件,未添加到管道流中!");
            }
        } catch (Exception e) {
            logger.error("返回下行数据响应信息过滤相关处理异常...", e);
        }
        
        // 继续处理当前信息,上行数据最后节点
        //ctx.fireChannelRead(upStream);
    }

}