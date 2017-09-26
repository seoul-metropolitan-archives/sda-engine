package com.bgf.shbank.domain.mng.cash.sh03001120.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by james on 17. 1. 24.
 */
public class Sh03001120SendMsgEncoder {

    public static ByteBuf encode(Sh03001120SendMsg msg) throws Exception {

        ByteBuf out = Unpooled.buffer(400);

		/* 지점코드 [N4] */
		out.writeBytes(msg.getBranchCode());
		/* 단말번호 [N4] */
		out.writeBytes(msg.getTerminalNo());
		/* 조회일자 [AN8] */
		out.writeBytes(msg.getReferDate());
		/* 조회전표번호 [N4] */
		out.writeBytes(msg.getReferStatementNo());
		/* 거래시간(조회시작) [AN6] */
		out.writeBytes(msg.getDealTimeReferStart());
		/* 거래시간(조회종료) [AN6] */
		out.writeBytes(msg.getDealTimeReferEnd());
		/* 출력결과건수 [AN2] */
		out.writeBytes(msg.getResultSize());
		/* next 여부 [AN2] */
		out.writeBytes(msg.getHasNext());
		/* 거래일련번호 [AN4] */
		out.writeBytes(msg.getDealSeqNo1());
		/* 거래시간 [AN6] */
		out.writeBytes(msg.getDealTime1());
		/* 처리상태 [AN2] */
		out.writeBytes(msg.getProcessStatus1());
		/* 입지구분 [AN2] */
		out.writeBytes(msg.getLocateGubun1());
		/* 거래매체 [AN2] */
		out.writeBytes(msg.getDealMethod1());
		/* 입금은행코드 [AN3] */
		out.writeBytes(msg.getDepositBankCode1());
		/* 입금계좌번호 [AN16] */
		out.writeBytes(msg.getDepositAccountNo1());
		/* 출금은행코드 [AN3] */
		out.writeBytes(msg.getWithdrawBankCode1());
		/* 출금계좌번호 [AN16] */
		out.writeBytes(msg.getWithdrawAccountNo1());
		/* 거래금액 [AN12] */
		out.writeBytes(msg.getDealAmt1());
		/* 거래일련번호 [AN4] */
		out.writeBytes(msg.getDealSeqNo2());
		/* 거래시간 [AN6] */
		out.writeBytes(msg.getDealTime2());
		/* 처리상태 [AN2] */
		out.writeBytes(msg.getProcessStatus2());
		/* 입지구분 [AN2] */
		out.writeBytes(msg.getLocateGubun2());
		/* 거래매체 [AN2] */
		out.writeBytes(msg.getDealMethod2());
		/* 입금은행코드 [AN3] */
		out.writeBytes(msg.getDepositBankCode2());
		/* 입금계좌번호 [AN16] */
		out.writeBytes(msg.getDepositAccountNo2());
		/* 출금은행코드 [AN3] */
		out.writeBytes(msg.getWithdrawBankCode2());
		/* 출금계좌번호 [AN16] */
		out.writeBytes(msg.getWithdrawAccountNo2());
		/* 거래금액 [AN12] */
		out.writeBytes(msg.getDealAmt2());
		/* 거래일련번호 [AN4] */
		out.writeBytes(msg.getDealSeqNo3());
		/* 거래시간 [AN6] */
		out.writeBytes(msg.getDealTime3());
		/* 처리상태 [AN2] */
		out.writeBytes(msg.getProcessStatus3());
		/* 입지구분 [AN2] */
		out.writeBytes(msg.getLocateGubun3());
		/* 거래매체 [AN2] */
		out.writeBytes(msg.getDealMethod3());
		/* 입금은행코드 [AN3] */
		out.writeBytes(msg.getDepositBankCode3());
		/* 입금계좌번호 [AN16] */
		out.writeBytes(msg.getDepositAccountNo3());
		/* 출금은행코드 [AN3] */
		out.writeBytes(msg.getWithdrawBankCode3());
		/* 출금계좌번호 [AN16] */
		out.writeBytes(msg.getWithdrawAccountNo3());
		/* 거래금액 [AN12] */
		out.writeBytes(msg.getDealAmt3());
		/* 거래일련번호 [AN4] */
		out.writeBytes(msg.getDealSeqNo4());
		/* 거래시간 [AN6] */
		out.writeBytes(msg.getDealTime4());
		/* 처리상태 [AN2] */
		out.writeBytes(msg.getProcessStatus4());
		/* 입지구분 [AN2] */
		out.writeBytes(msg.getLocateGubun4());
		/* 거래매체 [AN2] */
		out.writeBytes(msg.getDealMethod4());
		/* 입금은행코드 [AN3] */
		out.writeBytes(msg.getDepositBankCode4());
		/* 입금계좌번호 [AN16] */
		out.writeBytes(msg.getDepositAccountNo4());
		/* 출금은행코드 [AN3] */
		out.writeBytes(msg.getWithdrawBankCode4());
		/* 출금계좌번호 [AN16] */
		out.writeBytes(msg.getWithdrawAccountNo4());
		/* 거래금액 [AN12] */
		out.writeBytes(msg.getDealAmt4());
		/* 거래일련번호 [AN4] */
		out.writeBytes(msg.getDealSeqNo5());
		/* 거래시간 [AN6] */
		out.writeBytes(msg.getDealTime5());
		/* 처리상태 [AN2] */
		out.writeBytes(msg.getProcessStatus5());
		/* 입지구분 [AN2] */
		out.writeBytes(msg.getLocateGubun5());
		/* 거래매체 [AN2] */
		out.writeBytes(msg.getDealMethod5());
		/* 입금은행코드 [AN3] */
		out.writeBytes(msg.getDepositBankCode5());
		/* 입금계좌번호 [AN16] */
		out.writeBytes(msg.getDepositAccountNo5());
		/* 출금은행코드 [AN3] */
		out.writeBytes(msg.getWithdrawBankCode5());
		/* 출금계좌번호 [AN16] */
		out.writeBytes(msg.getWithdrawAccountNo5());
		/* 거래금액 [AN12] */
		out.writeBytes(msg.getDealAmt5());
		/* FILLER [AN34] */
		out.writeBytes(msg.getFiller());

        return out;
    }
}

