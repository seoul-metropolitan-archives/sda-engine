package com.bgf.shbank.core.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by tw.jang on 17. 1. 24.
 */
public class RecvMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        RecvSocketMsg recvMsg = new RecvSocketMsg();
        CommonInfo commonInfo = new CommonInfo();

        byte[] byteArray;

        // ===== [AP HEADER] [SIZE :: 22 Bytes] =====
		// TX ID [004]
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);

		// Reaction Code [001]
		byteArray = new byte[1];
		in.readBytes(byteArray, 0, 1);

		// Valid Check Code [003]
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);

		// Filler [014]
		byteArray = new byte[14];
		in.readBytes(byteArray, 0, 14);

		// ===== [공통 정보] [SIZE :: 90 Bytes] =====
		// LENGTH [004]
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);

		// 포맷타입 [002]
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		commonInfo.setFormatType(byteArray);

		// 거래일자 [008]
		byteArray = new byte[8];
		in.readBytes(byteArray, 0, 8);
		commonInfo.setTxDate(byteArray);

		// 거래시각 [006]
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		commonInfo.setTxTime(byteArray);

		// 신한은행 전문일련번호 [007]
		byteArray = new byte[7];
		in.readBytes(byteArray, 0, 7);
		commonInfo.setShbankSn(byteArray);

		// 제휴기관 전문일련번호 [007]
		byteArray = new byte[7];
		in.readBytes(byteArray, 0, 7);
		commonInfo.setCooperationSn(byteArray);

		// 전문코드 [004]
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		commonInfo.setMsgType(byteArray);

		// 업무구분 [004]
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		commonInfo.setTxType(byteArray);

		// 응답코드 [002]
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		commonInfo.setResCode(byteArray);

		// 제휴기관 번호 [002]
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		commonInfo.setCooperationNo(byteArray);

		// 전문구분 [001]
		byteArray = new byte[1];
		in.readBytes(byteArray, 0, 1);
		commonInfo.setStextType(byteArray);

		// 송신처구분 [001]
		byteArray = new byte[1];
		in.readBytes(byteArray, 0, 1);
		commonInfo.setSenderType(byteArray);

		// filler [042]
		byteArray = new byte[42];
		in.readBytes(byteArray, 0, 42);

		// ===== [업무 정보] [SIZE :: 400 Bytes] =====
		byteArray = new byte[400];
		in.readBytes(byteArray, 0, 400);

		recvMsg.setTaskId("sh" + commonInfo.getMsgType() + commonInfo.getTxType());
		recvMsg.setCommonInfo(commonInfo);
		recvMsg.setDataBytes(Unpooled.copiedBuffer(byteArray));

        out.add(recvMsg);
    }

}

