package com.saic.ebiz.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 基于http协议的文件服务器
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年4月11日
 * 
 */
@Deprecated
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/target/";

    public void start(final String host,final int port, final String url) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                           .channel(NioServerSocketChannel.class)
                           .childHandler(new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    protected void initChannel(SocketChannel arg0) throws Exception {
                                        arg0.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                                        arg0.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                                        arg0.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                                        arg0.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                                        arg0.pipeline().addLast("fileserver", new HttpFileServerHandler(url));
                                    }
                    });

            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址是:" + "http://"+host+":" + port + url);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        String host="127.0.0.1";
        new HttpFileServer().start(host,port, DEFAULT_URL);
    }
}