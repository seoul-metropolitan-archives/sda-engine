package com.bgf.shbank.core;

import com.bgf.shbank.core.code.JobStatus;
import com.bgf.shbank.core.code.RemoteGateway;
import com.bgf.shbank.core.message.RecvMsgDecoder;
import com.bgf.shbank.core.message.RecvSocketMsg;
import com.bgf.shbank.core.message.SendMsgEncoder;
import com.bgf.shbank.core.message.SendSocketMsg;
import com.bgf.shbank.domain.mng.cash.sh03001120.socket.Sh03001120ClientHandler;
import com.bgf.shbank.domain.mng.cash.sh03001120.socket.Sh03001120RecvMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by james on 2017-02-16.
 */
@Slf4j
@Component
public class MsgExchangeClient {

    @Value("${msg.exchange.gateway.online.security-corp.ip}")
    private String securityCorpIp;

    @Value("${msg.exchange.gateway.online.security-corp.port}")
    private int securityCorpPort;

    @Value("${msg.exchange.gateway.online.shbank.ip}")
    private String shbankIp;

    @Value("${msg.exchange.gateway.online.shbank.port}")
    private int shbankPort;

    public JobStatus send(SendSocketMsg sendSocketMsg, RemoteGateway remoteServer) {

        MsgExchangeClientHandler clientHandler = new MsgExchangeClientHandler(sendSocketMsg);

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = buildBootstrap(group, clientHandler);

            String ip = remoteServer == RemoteGateway.SH_BANK ? shbankIp : securityCorpIp;
            int port = remoteServer == RemoteGateway.SH_BANK ? shbankPort : securityCorpPort;

            ChannelFuture future = bootstrap.connect(ip, port).sync();
            future.channel().closeFuture().sync();

            return clientHandler.getJobStatus();

        } catch (Exception e) {

            log.error("MsgExchangeClientRunner-start :: {}", e.getMessage());

            return JobStatus.ERROR;

        } finally {
            group.shutdownGracefully();
        }
    }

    public <R> R sendAndReceive(SendSocketMsg sendSocketMsg, RemoteGateway remoteServer) {

        MsgExchangeClientHandler clientHandler = new MsgExchangeClientHandler(sendSocketMsg);

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = buildBootstrap(group, clientHandler);

            String ip = remoteServer == RemoteGateway.SH_BANK ? shbankIp : securityCorpIp;
            int port = remoteServer == RemoteGateway.SH_BANK ? shbankPort : securityCorpPort;

            ChannelFuture future = bootstrap.connect(ip, port).sync();
            future.channel().closeFuture().sync();

            return (R) clientHandler.getRecvMsg();

        } catch (Exception e) {
            log.error("MsgExchangeClientRunner-start :: {}", e.getMessage());
            return null;

        } finally {
            group.shutdownGracefully();
        }
    }

    public Sh03001120RecvMsg send(SendSocketMsg sendSocketMsg) {

        Sh03001120ClientHandler clientHandler = new Sh03001120ClientHandler(sendSocketMsg);

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = buildBootstrap(group, clientHandler);

            ChannelFuture future = bootstrap.connect(shbankIp, shbankPort).sync();
            future.channel().closeFuture().sync();

            return clientHandler.getRecvMsg();

        } catch (Exception e) {

            log.error("MsgExchangeClientRunner-start :: {}", e.getMessage());

            return clientHandler.getRecvMsg();

        } finally {
            group.shutdownGracefully();
        }
    }

    private <ClientHandler extends SimpleChannelInboundHandler<RecvSocketMsg>> Bootstrap buildBootstrap(EventLoopGroup group, ClientHandler clientHandler) {

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast("logger", new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast("idleHandler", new IdleStateHandler(30, 30, 0, TimeUnit.SECONDS));
                        pipeline.addLast("encoder", new SendMsgEncoder());
                        pipeline.addLast("frameDecoder", new FixedLengthFrameDecoder(512));
                        pipeline.addLast("decoder", new RecvMsgDecoder());
                        pipeline.addLast("handler", clientHandler);
                    }
                });

        return bootstrap;
    }

}
