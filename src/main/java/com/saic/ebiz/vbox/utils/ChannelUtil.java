package com.saic.ebiz.vbox.utils;

import io.netty.channel.Channel;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.saic.ebiz.vbox.conf.StreamConstant;

/**
 * Channel连接操作工具类,暂时测试使用,后续实际环境需要更新升级
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年10月28日
 *
 */
@Deprecated
public class ChannelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ChannelUtil.class);
    
    private static final ConcurrentMap<String, Channel> login_channels = new ConcurrentHashMap<String, Channel>();
	
	/**
	 * 新添加Channel
	 */
	public static synchronized Channel add(String sn, Channel channel) {
	    logger.debug(".add开始执行...");
	    Channel put =null;
	    if(channel!=null){
	        put = login_channels.put(sn, channel);
		    logger.debug(".add结果Channel不为空:{}",channel.toString());
        }else{
            logger.debug(".add结果Channel为空:{}",JSON.toJSONString(put));
        }
		return put;
	}
	
    /**
	 * 移除Channel
	 */
	public static synchronized Channel remove(Channel channel) {
	    if(channel==null){
	        return null;
	    }
	    logger.debug(".remove-Channel开始执行...");
	    Channel remove =null;
	    Set<Entry<String, Channel>> entries = login_channels.entrySet();
        for (Entry<String, Channel> entry : entries) {
            String sn = entry.getKey();
            Channel channel0 = entry.getValue();
            if (channel.equals(channel0)) {
                logger.warn(".remove-Channel执行remove[sn={}]结束...",sn);
                channel.attr(StreamConstant.ATTRIBUTEKEY).remove();
                channel.disconnect();
                channel.close();
                remove =login_channels.remove(sn);
                break;
            }
        }
	    if(remove!=null){
	        logger.debug(".remove-Channel结果Channel不为空:{}",remove.toString());
	    }else{
	        logger.debug(".remove-Channel结果Channel为空:{}",JSON.toJSONString(remove));
	    }
	    return remove;
	}
	
	public static synchronized Channel remove(String sn) {
	    logger.debug(".remove-sn开始执行...");
	    Channel remove = login_channels.remove(sn);
	    if(remove!=null){
	        logger.debug(".remove-sn结果Channel不为空:{}",remove.toString());
	    }else{
	        logger.debug(".remove-sn结果Channel为空:{}",JSON.toJSONString(remove));
	    }
	    return remove;
	}
	
	/**
	 * 获取Channel
	 */
	public static Channel get(String sn) {
	    logger.debug(".get开始执行...");
	    Channel channel = login_channels.get(sn);
	    if(channel!=null){
	        logger.warn(".get结果{},不为空:{}",channel.toString(),JSON.toJSONString(channel));
//            logger.warn(".get结果当前[sn={}]已连接服务器!其对应的Channel状态:[channel==null[{}],!(channel.isActive())=[{}],!(channel.isWritable())=[{}],!(channel.isOpen())=[{}]!",
//                    sn,channel==null,!(channel.isActive()),!(channel.isWritable()),!(channel.isOpen()));
        }else{
            logger.error(".get结果为null:{}",JSON.toJSONString(channel));
            logger.error(".get结果当前[commandKey={},sn={}]未连接服务器!其对应的Channel状态:[channel==null[{}]]!",sn,channel==null);
        }
	    logger.debug(".get执行结束...");
		return channel;
	}
	
	/**
	 * 获取key集合
	 */
	public static Set<String> keySet() {
        return login_channels.keySet();
    }
	
	/**
	 * 获取ChannelMap的size
	 */
	public static int getSize() {
	    logger.debug(".getSize开始执行...");
	    return login_channels.size();
	}
	
	/**
	 * 是否处于有效连接状态
	 */
	public static boolean isActive(String sn) {
	    logger.debug(".isActive开始执行...");
        Channel channel = login_channels.get(sn);
        logger.debug(".isActive执行结束...");
        return isActive(channel);
    }
	
	public static boolean isActive(Channel channel) {
	    logger.debug(".isActive2开始执行...");
	    if(channel!=null){
	        logger.warn(".isActive2开始执行Channel不为空...{}",channel.toString());
//	        logger.warn(".isActive2开始执行isActive=[{}],isWritable=[{}],isOpen=[{}]",
//	                channel.isActive(),channel.isWritable(),channel.isOpen());
//	        try {
//	            //ChannelFuture future = channel.closeFuture().sync();
//	            ChannelFuture future = channel.newSucceededFuture().sync();
//	            logger.debug(".isActive2开始执行ChannelFuture={}",JSON.toJSONString(future));
//	            if(future.channel()!=null){
//	                logger.warn(".isActive2开始执行ChannelFuture.channel()={},[{}]",
//	                        JSON.toJSONString(future.channel()),future.channel().toString());
//	                logger.warn("当前ChannelFuture.channel()状态:!(isActive())=[{}],!(isWritable())=[{}]]!",
//	                        !(future.channel().isActive()),!(future.channel().isWritable()));
//	            }else{
//	                logger.debug(".isActive2开始执行ChannelFuture.channel()={}",JSON.toJSONString(future.channel()));
//	            }
//	        } catch (InterruptedException e1) {
//	            logger.error(".isActive2执行异常{}",e1);
//	        }
	    }
	    logger.debug(".isActive2执行结束...{}",JSON.toJSONString(channel));
	    logger.debug(".isActive2执行结束...");
	    return channel != null && channel.isActive() && channel.isWritable() && channel.isOpen();
	}
	
	/**
     * 打印当前对应的所有的Channel的连接信息
     */
    public static void printChannelStatusMap() {
        StringBuilder sb=new StringBuilder();
        sb.append("\r\n");
        sb.append("\r\n********************printChannelStatusMap开始执行********************\r\n");
        sb.append("login_channels数据长度:"+login_channels.size()+"\r\n");
        for (Entry<String, Channel> entry : login_channels.entrySet()) {
            String key=entry.getKey();
            Channel channel = entry.getValue();
            sb.append(key+"============>>>");
            if(channel!=null){
                sb.append("isActive=["+channel.isActive()+"],");
                sb.append("isOpen=["+channel.isOpen()+"],");
                sb.append("isWritable=["+channel.isWritable()+"],");
            }
            sb.append(JSON.toJSONString(channel)+"\r\n");
        }
        sb.append("********************printChannelStatusMap执行结束********************");
        logger.warn(sb.toString());
    }
    
    private ChannelUtil() {
    }

}
