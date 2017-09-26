package com.bgf.shbank.domain.mng.cash.sh03001120.socket;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by james on 17. 1. 24.
 */
@Slf4j
public class Sh03001120RecvMsgDecoder {

    public static Sh03001120RecvMsg decode(ByteBuf in) throws Exception {

        Sh03001120RecvMsg message = new Sh03001120RecvMsg();
        byte[] byteArray;

		/* 지점코드 [N4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setBranchCode(byteArray);

		/* 단말번호 [N4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setTerminalNo(byteArray);

		/* 조회일자 [AN8] */
		byteArray = new byte[8];
		in.readBytes(byteArray, 0, 8);
		message.setReferDate(byteArray);

		/* 조회전표번호 [N4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setReferStatementNo(byteArray);

		/* 거래시간(조회시작) [AN6] */
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		message.setDealTimeReferStart(byteArray);

		/* 거래시간(조회종료) [AN6] */
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		message.setDealTimeReferEnd(byteArray);

		/* 출력결과건수 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setResultSize(byteArray);

		/* next 여부 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setHasNext(byteArray);

		/* 거래일련번호 [AN4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setDealSeqNo1(byteArray);

		/* 거래시간 [AN6] */
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		message.setDealTime1(byteArray);

		/* 처리상태 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setProcessStatus1(byteArray);

		/* 입지구분 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setLocateGubun1(byteArray);

		/* 거래매체 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setDealMethod1(byteArray);

		/* 입금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setDepositBankCode1(byteArray);

		/* 입금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setDepositAccountNo1(byteArray);

		/* 출금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setWithdrawBankCode1(byteArray);

		/* 출금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setWithdrawAccountNo1(byteArray);

		/* 거래금액 [AN12] */
		byteArray = new byte[12];
		in.readBytes(byteArray, 0, 12);
		message.setDealAmt1(byteArray);

		/* 거래일련번호 [AN4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setDealSeqNo2(byteArray);

		/* 거래시간 [AN6] */
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		message.setDealTime2(byteArray);

		/* 처리상태 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setProcessStatus2(byteArray);

		/* 입지구분 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setLocateGubun2(byteArray);

		/* 거래매체 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setDealMethod2(byteArray);

		/* 입금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setDepositBankCode2(byteArray);

		/* 입금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setDepositAccountNo2(byteArray);

		/* 출금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setWithdrawBankCode2(byteArray);

		/* 출금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setWithdrawAccountNo2(byteArray);

		/* 거래금액 [AN12] */
		byteArray = new byte[12];
		in.readBytes(byteArray, 0, 12);
		message.setDealAmt2(byteArray);

		/* 거래일련번호 [AN4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setDealSeqNo3(byteArray);

		/* 거래시간 [AN6] */
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		message.setDealTime3(byteArray);

		/* 처리상태 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setProcessStatus3(byteArray);

		/* 입지구분 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setLocateGubun3(byteArray);

		/* 거래매체 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setDealMethod3(byteArray);

		/* 입금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setDepositBankCode3(byteArray);

		/* 입금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setDepositAccountNo3(byteArray);

		/* 출금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setWithdrawBankCode3(byteArray);

		/* 출금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setWithdrawAccountNo3(byteArray);

		/* 거래금액 [AN12] */
		byteArray = new byte[12];
		in.readBytes(byteArray, 0, 12);
		message.setDealAmt3(byteArray);

		/* 거래일련번호 [AN4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setDealSeqNo4(byteArray);

		/* 거래시간 [AN6] */
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		message.setDealTime4(byteArray);

		/* 처리상태 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setProcessStatus4(byteArray);

		/* 입지구분 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setLocateGubun4(byteArray);

		/* 거래매체 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setDealMethod4(byteArray);

		/* 입금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setDepositBankCode4(byteArray);

		/* 입금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setDepositAccountNo4(byteArray);

		/* 출금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setWithdrawBankCode4(byteArray);

		/* 출금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setWithdrawAccountNo4(byteArray);

		/* 거래금액 [AN12] */
		byteArray = new byte[12];
		in.readBytes(byteArray, 0, 12);
		message.setDealAmt4(byteArray);

		/* 거래일련번호 [AN4] */
		byteArray = new byte[4];
		in.readBytes(byteArray, 0, 4);
		message.setDealSeqNo5(byteArray);

		/* 거래시간 [AN6] */
		byteArray = new byte[6];
		in.readBytes(byteArray, 0, 6);
		message.setDealTime5(byteArray);

		/* 처리상태 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setProcessStatus5(byteArray);

		/* 입지구분 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setLocateGubun5(byteArray);

		/* 거래매체 [AN2] */
		byteArray = new byte[2];
		in.readBytes(byteArray, 0, 2);
		message.setDealMethod5(byteArray);

		/* 입금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setDepositBankCode5(byteArray);

		/* 입금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setDepositAccountNo5(byteArray);

		/* 출금은행코드 [AN3] */
		byteArray = new byte[3];
		in.readBytes(byteArray, 0, 3);
		message.setWithdrawBankCode5(byteArray);

		/* 출금계좌번호 [AN16] */
		byteArray = new byte[16];
		in.readBytes(byteArray, 0, 16);
		message.setWithdrawAccountNo5(byteArray);

		/* 거래금액 [AN12] */
		byteArray = new byte[12];
		in.readBytes(byteArray, 0, 12);
		message.setDealAmt5(byteArray);

		/* FILLER [AN34] */
		byteArray = new byte[34];
		in.readBytes(byteArray, 0, 34);
		message.setFiller(byteArray);

        log.debug("[RECV_MSG][{}] {}", message.getClass().getSimpleName(), message);

        return message;
    }
}

