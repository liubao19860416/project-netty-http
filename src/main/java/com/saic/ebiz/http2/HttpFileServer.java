package com.saic.ebiz.http2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于http协议的文件服务器
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年4月11日
 * 
 */
public class HttpFileServer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;
    
    //监听的端口
    private Integer port ;
    //连接超时时间
    private Integer connectionTimeout;
    private Integer maxMessagesPer;
    private String localDir;
    
    public HttpFileServer() {
        super();
    }
    
    public HttpFileServer(Integer port, Integer connectionTimeout,
            Integer maxMessagesPer, String localDir) {
        this();
        this.port = port;
        this.connectionTimeout = connectionTimeout;
        this.maxMessagesPer = maxMessagesPer;
        this.localDir = localDir;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setMaxMessagesPer(Integer maxMessagesPer) {
        this.maxMessagesPer = maxMessagesPer;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        logger.warn("HttpFileServer启动开始。。。");
        try {
            this.bootstrap = new ServerBootstrap();
            this.bootstrap.group(bossGroup, workerGroup)
                           .channel(NioServerSocketChannel.class)
                           .option(ChannelOption.SO_BACKLOG, 100)
                           .option(ChannelOption.TCP_NODELAY, true)
                           .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.connectionTimeout)
                           //设置发送队列的最大长度。
                           .option(ChannelOption.MAX_MESSAGES_PER_READ, this.maxMessagesPer)
                           .option(ChannelOption.SO_KEEPALIVE, true)
                           .childHandler(new ChannelInitializer<SocketChannel>() {
                                    protected void initChannel(SocketChannel sc) throws Exception {
                                        sc.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                                        sc.pipeline().addLast("http-aggregator", new HttpObjectAggregator(64 * 1024));
                                        sc.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                                        sc.pipeline().addLast("http-handler", new HttpRequestHandler(localDir));
                                    }
                    });
            ChannelFuture channelFuture = this.bootstrap.bind(this.port).sync();
            logger.warn("HttpFileServer启动结束。。。");
            channelFuture.channel().closeFuture().sync();
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

    public static void main(String[] args) throws Exception {
        //new HttpFileServer().start();
        Integer port= 80;
        Integer connectionTimeout=10000;
        Integer maxMessagesPer = 100;
        String localDir = "d:/";
        new HttpFileServer(port, connectionTimeout, maxMessagesPer, localDir).start();
    }

}
