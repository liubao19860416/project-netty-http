package com.saic.ebiz.vbox.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.stream.base.AbstractStream;
import com.saic.ebiz.vbox.stream.base.DownStream;
import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.utils.ChannelUtil;

/**
 * 根据上行/下行AbstractStream数据,对对应的链路连接信息进行统一处理
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月23日
 * 
 */
@SuppressWarnings("deprecation")
@Sharable
@Component
public class SessionHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);
    // 用于统计当前有多少活跃连接
    private static final ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 接收上行AbstractStream数据
     */
    @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    if (msg instanceof AbstractStream) {
	        AbstractStream abstractStream=(AbstractStream) msg;
	        String sn0 = abstractStream.getSn();
	        String commandType0 = abstractStream.getCommandType();
	        logger.warn("SessionHandler.channelRead接收到的数据为commandType={},sn={}.",commandType0,sn0);
	        if (msg instanceof UpStream) {
	            logger.warn("SessionHandler.channelRead接收到的数据类型是UpStream类型.");
	            //绑定sn关系
                Channel channel = ctx.channel();
                String sn = channel.attr(StreamConstant.ATTRIBUTEKEY).get();
                if(StringUtils.isBlank(sn)||!sn0.equalsIgnoreCase(sn)){
                    sn = sn0;
                    channel.attr(StreamConstant.ATTRIBUTEKEY).set(sn);
                    logger.info(".channelRead()设备sn={}首次注册绑定到服务器Channel:{}...",sn,channel.toString());
                }else{
                    logger.info(".channelRead()设备sn={}已经注册绑定到服务器Channel了:{}...",sn,channel.toString());
                }
                //TODO 保存设备sn号与Channel的映射关系
                ChannelUtil.add(sn, channel);
                
                
	        }else{
	            logger.warn("SessionHandler.channelRead接收到的数据类型不是UpStream类型,不做处理.");
	        }
	    }else{
	        logger.warn("SessionHandler.channelRead接收到的数据类型不是AbstractStream类型,不做处理.");
	    }
        
        //不释放对象，直接调用下一个Handler
        ctx.fireChannelRead(msg);
	}

    /**
     * 发送下行AbstractStream数据
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof AbstractStream) {
            AbstractStream abstractStream=(AbstractStream) msg;
            String sn0 = abstractStream.getSn();
            String commandType0 = abstractStream.getCommandType();
            logger.warn("SessionHandler.write接收到的数据为commandType={},sn={}.",commandType0,sn0);
            if(msg instanceof DownStream){
                logger.warn("SessionHandler.write接收到的数据类型是DownStream类型.");
                //TODO
                
                
            }else{
                logger.warn("SessionHandler.write接收到的数据类型不是DownStream类型,不做处理.");
            }
        }else{
            logger.warn("SessionHandler.write接收到的数据类型不是AbstractStream类型,继续传递.");
        }
        
        //不释放对象，直接调用下一个Handler
        ctx.writeAndFlush(msg, promise);
    }

    /**
     * 添加Channel到ChannelGroup，连接关闭时会自动移出ChannelGroup
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CHANNELS.add(ctx.channel());
        logger.info("SessionHandler有一个新的Channel连接：{}", ctx.channel().toString());
        logger.info("channelActive当前共有{}个Channel连接", CHANNELS.size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String sn = channel.attr(StreamConstant.ATTRIBUTEKEY).get();
        logger.info("SessionHandler中[sn={}],Channel被关闭：{}",sn, ctx.channel().toString());
        logger.info("channelInactive当前共有{}个Channel连接", CHANNELS.size());
        //CHANNELS.remove(ctx.channel());
        
        //遍历显示当前channel信息
        Iterator<Channel> iterator = CHANNELS.iterator();
        StringBuffer sb=new StringBuffer();
        sb.append("\r\n当前有效的连接信息:\r\n");
        while(iterator.hasNext()){
            Channel next = iterator.next();
            if(next!=null){
                String sn0 = channel.attr(StreamConstant.ATTRIBUTEKEY).get();
                sb.append("sn:"+sn0+"==========>>Channel:"+next.toString()+"\r\n");
            }else{
                sb.append("sn:==========>>Channel为null!\r\n");
            }
        }
        logger.info(sb.toString());
    }
    
}
