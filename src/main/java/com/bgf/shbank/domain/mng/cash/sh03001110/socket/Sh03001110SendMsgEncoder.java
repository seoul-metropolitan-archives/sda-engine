package com.bgf.shbank.domain.mng.cash.sh03001110.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by tw.jang on 17. 1. 24.
 */
public class Sh03001110SendMsgEncoder {

    public static ByteBuf encode(Sh03001110SendMsg msg) throws Exception {

        ByteBuf out = Unpooled.buffer(400);

		/* 지점코드 [N4] */
		out.writeBytes(msg.getBranchCode());
		/* 단말번호 [N4] */
		out.writeBytes(msg.getTerminalNo());
		/* 현금출금액 [N15] */
		out.writeBytes(msg.getCashWithdrawAmt());
		/* 현금입금액 [N15] */
		out.writeBytes(msg.getCashDepositAmt());
		/* 현금잔액 [N15] */
		out.writeBytes(msg.getCashAmt());
		/* 수표출금액 [N15] */
		out.writeBytes(msg.getCheckWithdrawAmt());
		/* 수표입금액 [N15] */
		out.writeBytes(msg.getCheckDepositAmt());
		/* 수표지급가능매수 [N4] */
		out.writeBytes(msg.getCheckGiveEnableCount());
		/* 5만원권지급가능매수 [N5] */
		out.writeBytes(msg.getCash50kGiveEnableCount());
		/* FILLER [AN308] */
		out.writeBytes(msg.getFiller());

        return out;
    }
}

