package com.saic.ebiz.vbox;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saic.ebiz.vbox.conf.StreamConstant;
import com.saic.ebiz.vbox.handler.AutoDownStreamHandler;
import com.saic.ebiz.vbox.handler.ByteToStreamDecoder;
import com.saic.ebiz.vbox.handler.ExceptionHandler;
import com.saic.ebiz.vbox.handler.GprsIdleStateHandler;
import com.saic.ebiz.vbox.handler.SessionHandler;
import com.saic.ebiz.vbox.handler.StreamToByteEncoder;

/**
 * 服务初始化配置相关handler信息等
 */
@Component
public class GprsSocketServerInitializer extends ChannelInitializer<SocketChannel> {

	private static final Logger logger = LoggerFactory.getLogger(GprsSocketServerInitializer.class);
	
	/**
	 * 上行数据包完结标志~==0x7E
	 */
	//private ByteBuf[] delimiters = new ByteBuf[] {Unpooled.wrappedBuffer(new byte[] { '~' }) };
	private ByteBuf[] delimiters = new ByteBuf[] {Unpooled.wrappedBuffer(StreamConstant.STREAM_DELIMITERS) };
	
	/**
	 * 读数据最长等待时间，单位秒
	 */
	@Value("${ebiz.liteplus.gprs.readerIdleTime}")
	private int readerIdleTime;
	
	//@Autowired
	//private MongoDBHandler mongoDBHandler;
	
    public GprsSocketServerInitializer() {}

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	logger.info("初始化Channel Pipe...");
        ChannelPipeline pipeline = ch.pipeline();
        logger.info("添加Channel");
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, delimiters));
        pipeline.addLast(new ByteToStreamDecoder());
        //pipeline.addLast(mongoDBHandler);
        pipeline.addLast(new SessionHandler());
        pipeline.addLast(new StreamToByteEncoder());
        pipeline.addLast(new AutoDownStreamHandler());
        //最长等待时间1小时
        pipeline.addLast(new IdleStateHandler(this.readerIdleTime, 0, 0));
        pipeline.addLast(new GprsIdleStateHandler());
        pipeline.addLast(new ExceptionHandler());
        //自动释放内存空间,在DefaultChannelPipeline中自动添加了TailHandler来处理。
        //pipeline.addLast(TailHandler);
        logger.info("添加Channel结束...");
    }
}
