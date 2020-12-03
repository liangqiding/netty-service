package com.netty.service.config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

/**
 * Netty获取、发送数据及处理
 */
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline= channel.pipeline();
        //以下三个是Http的支持
        //http解码器
        pipeline.addLast(new HttpServerCodec());
        //支持写大数据流
        pipeline.addLast(new ChunkedWriteHandler());
        //http聚合器
        pipeline.addLast(new HttpObjectAggregator(1024*62));
        //webSocket支持,设置路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //获取客户端数据的编码格式
        channel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        //发送给客户端数据的编码格式
        channel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        //添加Netty处理类
        channel.pipeline().addLast(new NettyServerHandler());
    }
}


