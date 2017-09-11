package com.bgf.shbank.core.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by tw.jang on 2017-01-31.
 */
public class SendMsgEncoder extends MessageToByteEncoder<SendSocketMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SendSocketMsg sendSocketMsg, ByteBuf out) throws Exception {

        // ===== [AP HEADER] [SIZE :: 22 Bytes] =====
        out.writeBytes(ApHeader.TX_ID.getBytes(CharsetUtil.UTF_8));
        out.writeBytes(ApHeader.REACTION_CODE.getBytes(CharsetUtil.UTF_8));
        out.writeBytes(ApHeader.VALID_CHECK_CODE.getBytes(CharsetUtil.UTF_8));
        out.writeBytes(ApHeader.FILLER.getBytes(CharsetUtil.UTF_8));

        // ===== [공통 정보] [SIZE :: 90 Bytes] =====
        CommonInfo commonInfo = sendSocketMsg.getCommonInfo();
        out.writeBytes(commonInfo.getLength().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getFormatType().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getTxDate().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getTxTime().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getShbankSn().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getCooperationSn().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getMsgType().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getTxType().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getResCode().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getCooperationNo().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getStextType().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getSenderType().getBytes(CharsetUtil.UTF_8));
        out.writeBytes(commonInfo.getFiller().getBytes(CharsetUtil.UTF_8));

        // ===== [업무 정보] [SIZE :: 400 Bytes] =====
        out.writeBytes(sendSocketMsg.getDataBytes());

    }
}
