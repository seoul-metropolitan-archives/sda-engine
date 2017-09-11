package com.bgf.shbank.domain.mng.cash.sh03001120.socket;

import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by tw.jang on 17. 1. 24.
 */
@Getter
@NoArgsConstructor
public class Sh03001120SendMsg {

	/* 지점코드 [N4] */
	private byte[] branchCode;

	/* 단말번호 [N4] */
	private byte[] terminalNo;

	/* 조회일자 [AN8] */
	private byte[] referDate;

	/* 조회전표번호 [N4] */
	private byte[] referStatementNo = "0000".getBytes();

	/* 거래시간(조회시작) [AN6] */
	private byte[] dealTimeReferStart = StringUtils.leftPad("", 6, ' ').getBytes();

	/* 거래시간(조회종료) [AN6] */
	private byte[] dealTimeReferEnd = StringUtils.leftPad("", 6, ' ').getBytes();

	/* 출력결과건수 [AN2] */
	private byte[] resultSize = StringUtils.leftPad("", 2, ' ').getBytes();

	/* next 여부 [AN2] */
	private byte[] hasNext = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 거래일련번호 [AN4] */
	private byte[] dealSeqNo1 = StringUtils.leftPad("", 4, ' ').getBytes();

	/* 거래시간 [AN6] */
	private byte[] dealTime1 = StringUtils.leftPad("", 6, ' ').getBytes();

	/* 처리상태 [AN2] */
	private byte[] processStatus1 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입지구분 [AN2] */
	private byte[] locateGubun1 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 거래매체 [AN2] */
	private byte[] dealMethod1 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입금은행코드 [AN3] */
	private byte[] depositBankCode1 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 입금계좌번호 [AN16] */
	private byte[] depositAccountNo1 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 출금은행코드 [AN3] */
	private byte[] withdrawBankCode1 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 출금계좌번호 [AN16] */
	private byte[] withdrawAccountNo1 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 거래금액 [AN12] */
	private byte[] dealAmt1 = StringUtils.leftPad("", 12, ' ').getBytes();

	/* 거래일련번호 [AN4] */
	private byte[] dealSeqNo2 = StringUtils.leftPad("", 4, ' ').getBytes();

	/* 거래시간 [AN6] */
	private byte[] dealTime2 = StringUtils.leftPad("", 6, ' ').getBytes();

	/* 처리상태 [AN2] */
	private byte[] processStatus2 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입지구분 [AN2] */
	private byte[] locateGubun2 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 거래매체 [AN2] */
	private byte[] dealMethod2 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입금은행코드 [AN3] */
	private byte[] depositBankCode2 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 입금계좌번호 [AN16] */
	private byte[] depositAccountNo2 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 출금은행코드 [AN3] */
	private byte[] withdrawBankCode2 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 출금계좌번호 [AN16] */
	private byte[] withdrawAccountNo2 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 거래금액 [AN12] */
	private byte[] dealAmt2 = StringUtils.leftPad("", 12, ' ').getBytes();

	/* 거래일련번호 [AN4] */
	private byte[] dealSeqNo3 = StringUtils.leftPad("", 4, ' ').getBytes();

	/* 거래시간 [AN6] */
	private byte[] dealTime3 = StringUtils.leftPad("", 6, ' ').getBytes();

	/* 처리상태 [AN2] */
	private byte[] processStatus3 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입지구분 [AN2] */
	private byte[] locateGubun3 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 거래매체 [AN2] */
	private byte[] dealMethod3 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입금은행코드 [AN3] */
	private byte[] depositBankCode3 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 입금계좌번호 [AN16] */
	private byte[] depositAccountNo3 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 출금은행코드 [AN3] */
	private byte[] withdrawBankCode3 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 출금계좌번호 [AN16] */
	private byte[] withdrawAccountNo3 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 거래금액 [AN12] */
	private byte[] dealAmt3 = StringUtils.leftPad("", 12, ' ').getBytes();

	/* 거래일련번호 [AN4] */
	private byte[] dealSeqNo4 = StringUtils.leftPad("", 4, ' ').getBytes();

	/* 거래시간 [AN6] */
	private byte[] dealTime4 = StringUtils.leftPad("", 6, ' ').getBytes();

	/* 처리상태 [AN2] */
	private byte[] processStatus4 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입지구분 [AN2] */
	private byte[] locateGubun4 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 거래매체 [AN2] */
	private byte[] dealMethod4 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입금은행코드 [AN3] */
	private byte[] depositBankCode4 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 입금계좌번호 [AN16] */
	private byte[] depositAccountNo4 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 출금은행코드 [AN3] */
	private byte[] withdrawBankCode4 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 출금계좌번호 [AN16] */
	private byte[] withdrawAccountNo4 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 거래금액 [AN12] */
	private byte[] dealAmt4 = StringUtils.leftPad("", 12, ' ').getBytes();

	/* 거래일련번호 [AN4] */
	private byte[] dealSeqNo5 = StringUtils.leftPad("", 4, ' ').getBytes();

	/* 거래시간 [AN6] */
	private byte[] dealTime5 = StringUtils.leftPad("", 6, ' ').getBytes();

	/* 처리상태 [AN2] */
	private byte[] processStatus5 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입지구분 [AN2] */
	private byte[] locateGubun5 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 거래매체 [AN2] */
	private byte[] dealMethod5 = StringUtils.leftPad("", 2, ' ').getBytes();

	/* 입금은행코드 [AN3] */
	private byte[] depositBankCode5 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 입금계좌번호 [AN16] */
	private byte[] depositAccountNo5 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 출금은행코드 [AN3] */
	private byte[] withdrawBankCode5 = StringUtils.leftPad("", 3, ' ').getBytes();

	/* 출금계좌번호 [AN16] */
	private byte[] withdrawAccountNo5 = StringUtils.leftPad("", 16, ' ').getBytes();

	/* 거래금액 [AN12] */
	private byte[] dealAmt5 = StringUtils.leftPad("", 12, ' ').getBytes();

	/* FILLER [AN34] */
	private byte[] filler = StringUtils.rightPad("", 34, ' ').getBytes(CharsetUtil.UTF_8);

	public void setBranchCode(String branchCode) {
		branchCode = StringUtils.leftPad(String.valueOf(branchCode), 4, '0');
		this.branchCode = branchCode.getBytes(CharsetUtil.UTF_8);
	}

	public void setTerminalNo(String terminalNo) {
		terminalNo = StringUtils.leftPad(String.valueOf(terminalNo), 4, '0');
		this.terminalNo = terminalNo.getBytes(CharsetUtil.UTF_8);
	}

	public void setReferDate(String referDate) {
		referDate = StringUtils.rightPad(referDate, 8, ' ');
		this.referDate = referDate.getBytes(CharsetUtil.UTF_8);
	}

	public void setReferStatementNo(String referStatementNo) {
		referStatementNo = StringUtils.leftPad(String.valueOf(referStatementNo), 4, '0');
		this.referStatementNo = referStatementNo.getBytes(CharsetUtil.UTF_8);
	}

	public void setDealTimeReferStart(String dealTimeReferStart) {
		dealTimeReferStart = StringUtils.rightPad(dealTimeReferStart, 6, ' ');
		this.dealTimeReferStart = dealTimeReferStart.getBytes(CharsetUtil.UTF_8);
	}

	public void setDealTimeReferEnd(String dealTimeReferEnd) {
		dealTimeReferEnd = StringUtils.rightPad(dealTimeReferEnd, 6, ' ');
		this.dealTimeReferEnd = dealTimeReferEnd.getBytes(CharsetUtil.UTF_8);
	}

}
