package com.netty.service.config;

import com.netty.service.util.MyDateUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    private static String address;

    private static String port;

    @Value("${netty.port}")
    private void setPort(String p) {
        port = p;
    }

    static {
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 管理一个全局map，保存连接进服务端的通道数量
     */

    private static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 只要有客户端连接就进行这个方法
     *
     * @param ctx ·
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        int clientPort = insocket.getPort();
        //获取连接通道唯一标识
        ChannelId channelId = ctx.channel().id();
        System.out.println();
        //如果map中不包含此连接，就保存连接
        if (CHANNEL_MAP.containsKey(channelId)) {
            log.info("客户端【" + channelId + "】是连接状态，连接通道数量: " + CHANNEL_MAP.size());
        } else {
            //保存连接
            CHANNEL_MAP.put(channelId, ctx);
            log.info("客户端【" + channelId + "】连接netty服务器[IP:" + clientIp + "--->PORT:" + clientPort + "]");
            log.info("连接通道数量: " + CHANNEL_MAP.size());
        }
    }

    /**
     * 客户端终止连接就进入
     *
     * @param ctx ·
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        //获取终止连接的客户端ID
        ChannelId channelId = ctx.channel().id();
        //包含此客户端才去删除
        if (CHANNEL_MAP.containsKey(channelId)) {
            //删除连接
            CHANNEL_MAP.remove(channelId);
            System.out.println();
            log.info("客户端【" + channelId + "】退出netty服务器[IP:" + clientIp + "--->PORT:" + insocket.getPort() + "]");
            log.info("连接通道数量: " + CHANNEL_MAP.size());
        }
    }

    /**
     * @param ctx ·
     *            DESCRIPTION: 有客户端发消息会触发此函数
     * @return: void
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inSocket.getAddress().getHostAddress();
        //获取终止连接的客户端ID
        //文本消息
        if (msg instanceof TextWebSocketFrame) {
            //第一次连接成功后，给客户端发送消息
            TextWebSocketFrame msgText = (TextWebSocketFrame) msg;
            Channel channel = ctx.channel();
            channel.writeAndFlush(new TextWebSocketFrame(MyDateUtils.dateFormat() + " 服务器[" + address + ":" + port + "]----收到消息：" + msgText.text()));
            //获取当前channel绑定的IP地址
            InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String address = ipSocket.getAddress().getHostAddress();
            log.info("address为:" + address + ",msg-------------->>>>>>>>>>>>" + msgText.text());
            //将IP和channel的关系保存
        }
        //二进制消息
        if (msg instanceof BinaryWebSocketFrame) {
            log.info("收到二进制消息：" + ((BinaryWebSocketFrame) msg).content().readableBytes());
            BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(Unpooled.buffer().writeBytes("hello".getBytes()));
            //给客户端发送的消息
            ctx.channel().writeAndFlush(binaryWebSocketFrame);
        }
        //ping消息
        if (msg instanceof PongWebSocketFrame) {
            log.info("客户端ping成功");
        }
        //关闭消息
        if (msg instanceof CloseWebSocketFrame) {
            log.info("客户端关闭，通道关闭");
            Channel channel = ctx.channel();
            channel.close();
        }
    }

    /**
     * @param msg       需要发送的消息内容
     * @param channelId 连接通道唯一id
     *                  DESCRIPTION: 服务端给客户端发送消息
     * @return: void
     */
    public void channelWrite(ChannelId channelId, Object msg) throws Exception {
        ChannelHandlerContext ctx = CHANNEL_MAP.get(channelId);
        if (ctx == null) {
            log.info("通道【" + channelId + "】不存在");
            return;
        }
        //将客户端的信息直接返回写入ctx
        ctx.write(msg);
        //刷新缓存区
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Client: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
    }

    /**
     * 连接错误时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(ctx.channel().id() + " 发生了错误," + "此时连通数量: " + CHANNEL_MAP.size());
        if ("重连".equals(cause.getMessage())) {
            log.info("触发重连");
            return;
        }
        cause.printStackTrace();
        ctx.close();
    }


}

