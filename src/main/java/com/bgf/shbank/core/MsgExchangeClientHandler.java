package com.bgf.shbank.core;

import com.bgf.shbank.core.code.JobStatus;
import com.bgf.shbank.core.message.RecvSocketMsg;
import com.bgf.shbank.core.message.SendSocketMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by james on 16. 3. 28.
 */
@Slf4j
public class MsgExchangeClientHandler extends SimpleChannelInboundHandler<RecvSocketMsg> {

    private SendSocketMsg sendSocketMsg;

    private String txId;

    @Getter
    private JobStatus jobStatus = JobStatus.SUCCESS;

    @Getter
    private Object recvMsg;

    public MsgExchangeClientHandler(SendSocketMsg sendSocketMsg) {
        this.sendSocketMsg = sendSocketMsg;
        this.txId = sendSocketMsg.getCommonInfo().getMsgType() + sendSocketMsg.getCommonInfo().getTxType();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        String remoteAddress = ctx.channel().remoteAddress().toString();

        log.info("[REMOTE :: {}] 원격 서버에 접속하였습니다. [Server: {}]", txId, remoteAddress);

        ctx.writeAndFlush(sendSocketMsg)
           .addListener(future -> log.debug("[REMOTE :: {}] 메시지 작성 및 서버로의 전송 작업이 완료되었습니다.", txId));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RecvSocketMsg recvSocketMsg) throws Exception {
        recvMsg = TaskWorker.buildRecvMsg(recvSocketMsg).get();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close()
           .addListener(future -> log.info("[REMOTE :: {}] 서버와의 채널 연결이 성공적으로 해제되었습니다.", txId));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        log.error("[REMOTE :: {}] MsgExchangeClientHandler-exceptionCaught :: {}", txId, cause.getMessage());

        jobStatus = JobStatus.ERROR;
        ctx.close();
    }
}
