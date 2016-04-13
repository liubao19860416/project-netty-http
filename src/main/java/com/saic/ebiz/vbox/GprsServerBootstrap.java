package com.saic.ebiz.vbox;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 服务启动入口
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2015年10月13日
 *
 */
public class GprsServerBootstrap {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	private ServerBootstrap bootstrap;
	private ChannelFuture channelFuture;
	//监听的端口
	private int port;
	//连接超时时间
	private int connectionTimeout;
	
	@Autowired
	private GprsSocketServerInitializer gprsSocketServerInitializer;
	
	public GprsServerBootstrap(){}
	
	public GprsServerBootstrap(int port,int connectionTimeout){
	    this.connectionTimeout = connectionTimeout;
		this.port = port;
	}
	
	/**
	 * 初始化启动方法
	 */
    public void start() throws Exception {
		logger.info(":开始启动Socket Server......");
    	EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	this.bootstrap = new ServerBootstrap();
        	this.bootstrap.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
//             .handler(new LoggingHandler(LogLevel.INFO))
             .option(ChannelOption.TCP_NODELAY, true)
             .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.connectionTimeout)
             //设置发送队列的最大长度。
             .option(ChannelOption.MAX_MESSAGES_PER_READ, 100)
             .option(ChannelOption.SO_KEEPALIVE, true)
             .childHandler(gprsSocketServerInitializer);
            this.channelFuture = this.bootstrap.bind(this.port).sync();
            
            logger.info(":启动Socket Server结束...");
            this.channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

	/**
	 * 销毁方法
	 */
	public void stop() throws Exception {
		logger.info(this.getClass().getName()+":正在关闭Socket Server...");
		this.channelFuture.channel().closeFuture().sync();
	}

	public void setPort(int port) {
		this.port = port;
	}

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

}
