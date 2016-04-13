package com.saic.ebiz.vbox.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.StringUtil;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.saic.ebiz.vbox.stream.base.UpStream;
import com.saic.ebiz.vbox.utils.StreamFactory;

/**
 * 解析上行ByteBuf格式数据流，创建UpStream对象
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年1月19日
 *
 */
@Sharable
@Component
public class ByteToStreamDecoder extends MessageToMessageDecoder<ByteBuf> {

	private static final Logger logger = LoggerFactory.getLogger(ByteToStreamDecoder.class);
	
    @Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
	    
		if (msg.readableBytes() > 0) {
		    
            //工具类测试
		    //ByteBufUtil.encodeString(alloc, new CharBuffer(msg), Charset.forName("UTF-8"));
            String hexDump = ByteBufUtil.hexDump(msg);
            hexDump = ByteBufUtil.hexDump(msg, 0, msg.capacity());
            logger.warn("获取hexDump信息:{}",hexDump);
            
            //1 字节长度值
            @SuppressWarnings("unused")
            short unsignedByte = msg.getUnsignedByte(0);
            
		    //获取会话的sessionId信息
		    long sessionId = msg.getLong(0);
		    logger.warn("获取会话的sessionId信息:{}",sessionId);
		    
		    //获取当前Channel对象
		    /*final Channel channel = ctx.channel();
		    int connectTimeoutMillis = channel.config().getConnectTimeoutMillis();
		    if(connectTimeoutMillis>0){
		        //连接超时
		        channel.eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {
                        ChannelPromise voidPromise = channel.voidPromise();
                        if(voidPromise!=null){
                            try {
                                //执行逻辑位置
                                Throwable cause=new ConnectTimeoutException("connection time out");
                                voidPromise.tryFailure(cause);
                                voidPromise.trySuccess();
                            }finally{
                                //释放资源位置
                                channel.close(voidPromise);
                            }
                        }
                    }
                }, connectTimeoutMillis, TimeUnit.MILLISECONDS);
		        
		    }*/
		    
		    //连接结束，取消定时器
		    //channel.closeFuture().cancel(false);
		    
		    //动态注册监听器
		    //ChannelFuture channelFuture = ctx.channel().close();
		    //@SuppressWarnings("rawtypes")
            //GenericFutureListener listener = new MyGenericFutureListener();
		    //channelFuture.addListener(listener);
		    /*channelFuture.addListener(new ChannelFutureListener() {
		        //内部回调实现,可以实现其他业务功能
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    @SuppressWarnings("unused")
                    Void void1 = future.get();
                    
                }
            });*/
		    
			byte[] dataStream = new byte[msg.readableBytes()];
	        msg.getBytes(0, dataStream);
	
	        logger.info("接收到的原始HEX数据:{}", StringUtil.toHexStringPadded(dataStream));
	        //logger.info("接收到数据，ASCII：{}", new String(dataStream,StreamConstant.CHARSET_DEFAULT));
	        
	        UpStream upStream = StreamFactory.buildUpStream(dataStream);
	        if(upStream!=null){
                out.add(upStream);
            }else{
                logger.error("解析得到的UpStream数据为空!");
            }
		}else{
		    //释放对象，一般在try-finally语句结构中的finally中使用
            //ReferenceCountUtil.release(msg);
            logger.info("接收到的上行指令信息体为空!!!");
		}
		
	}
	
}
