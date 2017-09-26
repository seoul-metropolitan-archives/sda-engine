package com.bgf.shbank.domain.mng.cash.sh03001110.socket;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by james on 17. 1. 24.
 */
@Slf4j
public class Sh03001110RecvMsgDecoder {

    public static Sh03001110RecvMsg decode(ByteBuf in) throws Exception {

        Sh03001110RecvMsg message = new Sh03001110RecvMsg();
        byte[] byteArray;

		/* 지점코드 [N4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setBranchCode(byteArray);

		/* 단말번호 [N4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setTerminalNo(byteArray);

		/* 현금출금액 [N15] */
		byteArray = new byte[15];
		in.readBytes(byteArray, 0, 15);
		message.setCashWithdrawAmt(byteArray);

		/* 현금입금액 [N15] */
		byteArray = new byte[15];
		in.readBytes(byteArray, 0, 15);
		message.setCashDepositAmt(byteArray);

		/* 현금잔액 [N15] */
		byteArray = new byte[15];
		in.readBytes(byteArray, 0, 15);
		message.setCashAmt(byteArray);

		/* 수표출금액 [N15] */
		byteArray = new byte[15];
		in.readBytes(byteArray, 0, 15);
		message.setCheckWithdrawAmt(byteArray);

		/* 수표입금액 [N15] */
		byteArray = new byte[15];
		in.readBytes(byteArray, 0, 15);
		message.setCheckDepositAmt(byteArray);

		/* 수표지급가능매수 [N4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setCheckGiveEnableCount(byteArray);

		/* 5만원권지급가능매수 [N5] */
		byteArray = new byte[5];
		in.readBytes(byteArray, 0, 5);
		message.setCash50kGiveEnableCount(byteArray);

		/* FILLER [AN308] */
		byteArray = new byte[308];
		in.readBytes(byteArray, 0, 308);
		message.setFiller(byteArray);

        log.debug("[RECV_MSG][{}] {}", message.getClass().getSimpleName(), message);

        return message;
    }
}

