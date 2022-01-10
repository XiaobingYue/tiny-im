package com.yxb.tinylove.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yxb
 * @since 2021/12/15
 */

@Slf4j
@Component
public class WsStarter {

    private NioEventLoopGroup boss;
    private NioEventLoopGroup work;
    private ChannelFuture channelFuture;

    @Autowired
    private WsHandler wsHandler;

    public void start(int port) {
        log.info("开始启动ws服务...");
        boss = new NioEventLoopGroup();
        work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast("logging", new LoggingHandler("DEBUG"));
                            socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
                            socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));//聚合器，使用websocket会用到
                            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());//用于大数据的分区传输
                            socketChannel.pipeline().addLast("handler", wsHandler);//自定义的业务handler;
                        }
                    });
            channelFuture = bootstrap.bind(port).sync();
            log.info("ws服务启动完成...");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void destroy() {
        try {
            log.info("开始停止ws服务...");
            channelFuture.channel().close();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            log.info("ws服务已停止...");
        }
    }

}
