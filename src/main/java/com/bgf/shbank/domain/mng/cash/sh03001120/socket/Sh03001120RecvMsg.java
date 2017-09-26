package com.bgf.shbank.domain.mng.cash.sh03001120.socket;

import com.bgf.shbank.core.message.SocketMsgToStringStyle;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by james on 17. 1. 24.
 */
@Getter
@NoArgsConstructor
public class Sh03001120RecvMsg {

	/* 지점코드 [N4] */
	private String branchCode;

	/* 단말번호 [N4] */
	private String terminalNo;

	/* 조회일자 [AN8] */
	private String referDate;

	/* 조회전표번호 [N4] */
	private String referStatementNo;

	/* 거래시간(조회시작) [AN6] */
	private String dealTimeReferStart;

	/* 거래시간(조회종료) [AN6] */
	private String dealTimeReferEnd;

	/* 출력결과건수 [AN2] */
	private String resultSize;

	/* next 여부 [AN2] */
	private String hasNext;

	/* 거래일련번호 [AN4] */
	private String dealSeqNo1;

	/* 거래시간 [AN6] */
	private String dealTime1;

	/* 처리상태 [AN2] */
	private String processStatus1;

	/* 입지구분 [AN2] */
	private String locateGubun1;

	/* 거래매체 [AN2] */
	private String dealMethod1;

	/* 입금은행코드 [AN3] */
	private String depositBankCode1;

	/* 입금계좌번호 [AN16] */
	private String depositAccountNo1;

	/* 출금은행코드 [AN3] */
	private String withdrawBankCode1;

	/* 출금계좌번호 [AN16] */
	private String withdrawAccountNo1;

	/* 거래금액 [AN12] */
	private String dealAmt1;

	/* 거래일련번호 [AN4] */
	private String dealSeqNo2;

	/* 거래시간 [AN6] */
	private String dealTime2;

	/* 처리상태 [AN2] */
	private String processStatus2;

	/* 입지구분 [AN2] */
	private String locateGubun2;

	/* 거래매체 [AN2] */
	private String dealMethod2;

	/* 입금은행코드 [AN3] */
	private String depositBankCode2;

	/* 입금계좌번호 [AN16] */
	private String depositAccountNo2;

	/* 출금은행코드 [AN3] */
	private String withdrawBankCode2;

	/* 출금계좌번호 [AN16] */
	private String withdrawAccountNo2;

	/* 거래금액 [AN12] */
	private String dealAmt2;

	/* 거래일련번호 [AN4] */
	private String dealSeqNo3;

	/* 거래시간 [AN6] */
	private String dealTime3;

	/* 처리상태 [AN2] */
	private String processStatus3;

	/* 입지구분 [AN2] */
	private String locateGubun3;

	/* 거래매체 [AN2] */
	private String dealMethod3;

	/* 입금은행코드 [AN3] */
	private String depositBankCode3;

	/* 입금계좌번호 [AN16] */
	private String depositAccountNo3;

	/* 출금은행코드 [AN3] */
	private String withdrawBankCode3;

	/* 출금계좌번호 [AN16] */
	private String withdrawAccountNo3;

	/* 거래금액 [AN12] */
	private String dealAmt3;

	/* 거래일련번호 [AN4] */
	private String dealSeqNo4;

	/* 거래시간 [AN6] */
	private String dealTime4;

	/* 처리상태 [AN2] */
	private String processStatus4;

	/* 입지구분 [AN2] */
	private String locateGubun4;

	/* 거래매체 [AN2] */
	private String dealMethod4;

	/* 입금은행코드 [AN3] */
	private String depositBankCode4;

	/* 입금계좌번호 [AN16] */
	private String depositAccountNo4;

	/* 출금은행코드 [AN3] */
	private String withdrawBankCode4;

	/* 출금계좌번호 [AN16] */
	private String withdrawAccountNo4;

	/* 거래금액 [AN12] */
	private String dealAmt4;

	/* 거래일련번호 [AN4] */
	private String dealSeqNo5;

	/* 거래시간 [AN6] */
	private String dealTime5;

	/* 처리상태 [AN2] */
	private String processStatus5;

	/* 입지구분 [AN2] */
	private String locateGubun5;

	/* 거래매체 [AN2] */
	private String dealMethod5;

	/* 입금은행코드 [AN3] */
	private String depositBankCode5;

	/* 입금계좌번호 [AN16] */
	private String depositAccountNo5;

	/* 출금은행코드 [AN3] */
	private String withdrawBankCode5;

	/* 출금계좌번호 [AN16] */
	private String withdrawAccountNo5;

	/* 거래금액 [AN12] */
	private String dealAmt5;

	/* FILLER [AN34] */
	private String filler;


	public void setBranchCode(byte[] bytes) {
		this.branchCode = new String(bytes, CharsetUtil.UTF_8);
		this.branchCode = StringUtils.trim(this.branchCode);
	}

	public void setTerminalNo(byte[] bytes) {
		this.terminalNo = new String(bytes, CharsetUtil.UTF_8);
		this.terminalNo = StringUtils.trim(this.terminalNo);
	}

	public void setReferDate(byte[] bytes) {
		this.referDate = new String(bytes, CharsetUtil.UTF_8);
		this.referDate = StringUtils.trim(this.referDate);
	}

	public void setReferStatementNo(byte[] bytes) {
		this.referStatementNo = new String(bytes, CharsetUtil.UTF_8);
		this.referStatementNo = StringUtils.trim(this.referStatementNo);
	}

	public void setDealTimeReferStart(byte[] bytes) {
		this.dealTimeReferStart = new String(bytes, CharsetUtil.UTF_8);
		this.dealTimeReferStart = StringUtils.trim(this.dealTimeReferStart);
	}

	public void setDealTimeReferEnd(byte[] bytes) {
		this.dealTimeReferEnd = new String(bytes, CharsetUtil.UTF_8);
		this.dealTimeReferEnd = StringUtils.trim(this.dealTimeReferEnd);
	}

	public void setResultSize(byte[] bytes) {
		this.resultSize = new String(bytes, CharsetUtil.UTF_8);
		this.resultSize = StringUtils.trim(this.resultSize);
	}

	public void setHasNext(byte[] bytes) {
		this.hasNext = new String(bytes, CharsetUtil.UTF_8);
		this.hasNext = StringUtils.trim(this.hasNext);
	}

	public void setDealSeqNo1(byte[] bytes) {
		this.dealSeqNo1 = new String(bytes, CharsetUtil.UTF_8);
		this.dealSeqNo1 = StringUtils.trim(this.dealSeqNo1);
	}

	public void setDealTime1(byte[] bytes) {
		this.dealTime1 = new String(bytes, CharsetUtil.UTF_8);
		this.dealTime1 = StringUtils.trim(this.dealTime1);
	}

	public void setProcessStatus1(byte[] bytes) {
		this.processStatus1 = new String(bytes, CharsetUtil.UTF_8);
		this.processStatus1 = StringUtils.trim(this.processStatus1);
	}

	public void setLocateGubun1(byte[] bytes) {
		this.locateGubun1 = new String(bytes, CharsetUtil.UTF_8);
		this.locateGubun1 = StringUtils.trim(this.locateGubun1);
	}

	public void setDealMethod1(byte[] bytes) {
		this.dealMethod1 = new String(bytes, CharsetUtil.UTF_8);
		this.dealMethod1 = StringUtils.trim(this.dealMethod1);
	}

	public void setDepositBankCode1(byte[] bytes) {
		this.depositBankCode1 = new String(bytes, CharsetUtil.UTF_8);
		this.depositBankCode1 = StringUtils.trim(this.depositBankCode1);
	}

	public void setDepositAccountNo1(byte[] bytes) {
		this.depositAccountNo1 = new String(bytes, CharsetUtil.UTF_8);
		this.depositAccountNo1 = StringUtils.trim(this.depositAccountNo1);
	}

	public void setWithdrawBankCode1(byte[] bytes) {
		this.withdrawBankCode1 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawBankCode1 = StringUtils.trim(this.withdrawBankCode1);
	}

	public void setWithdrawAccountNo1(byte[] bytes) {
		this.withdrawAccountNo1 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawAccountNo1 = StringUtils.trim(this.withdrawAccountNo1);
	}

	public void setDealAmt1(byte[] bytes) {
		this.dealAmt1 = new String(bytes, CharsetUtil.UTF_8);
		this.dealAmt1 = StringUtils.trim(this.dealAmt1);
	}

	public void setDealSeqNo2(byte[] bytes) {
		this.dealSeqNo2 = new String(bytes, CharsetUtil.UTF_8);
		this.dealSeqNo2 = StringUtils.trim(this.dealSeqNo2);
	}

	public void setDealTime2(byte[] bytes) {
		this.dealTime2 = new String(bytes, CharsetUtil.UTF_8);
		this.dealTime2 = StringUtils.trim(this.dealTime2);
	}

	public void setProcessStatus2(byte[] bytes) {
		this.processStatus2 = new String(bytes, CharsetUtil.UTF_8);
		this.processStatus2 = StringUtils.trim(this.processStatus2);
	}

	public void setLocateGubun2(byte[] bytes) {
		this.locateGubun2 = new String(bytes, CharsetUtil.UTF_8);
		this.locateGubun2 = StringUtils.trim(this.locateGubun2);
	}

	public void setDealMethod2(byte[] bytes) {
		this.dealMethod2 = new String(bytes, CharsetUtil.UTF_8);
		this.dealMethod2 = StringUtils.trim(this.dealMethod2);
	}

	public void setDepositBankCode2(byte[] bytes) {
		this.depositBankCode2 = new String(bytes, CharsetUtil.UTF_8);
		this.depositBankCode2 = StringUtils.trim(this.depositBankCode2);
	}

	public void setDepositAccountNo2(byte[] bytes) {
		this.depositAccountNo2 = new String(bytes, CharsetUtil.UTF_8);
		this.depositAccountNo2 = StringUtils.trim(this.depositAccountNo2);
	}

	public void setWithdrawBankCode2(byte[] bytes) {
		this.withdrawBankCode2 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawBankCode2 = StringUtils.trim(this.withdrawBankCode2);
	}

	public void setWithdrawAccountNo2(byte[] bytes) {
		this.withdrawAccountNo2 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawAccountNo2 = StringUtils.trim(this.withdrawAccountNo2);
	}

	public void setDealAmt2(byte[] bytes) {
		this.dealAmt2 = new String(bytes, CharsetUtil.UTF_8);
		this.dealAmt2 = StringUtils.trim(this.dealAmt2);
	}

	public void setDealSeqNo3(byte[] bytes) {
		this.dealSeqNo3 = new String(bytes, CharsetUtil.UTF_8);
		this.dealSeqNo3 = StringUtils.trim(this.dealSeqNo3);
	}

	public void setDealTime3(byte[] bytes) {
		this.dealTime3 = new String(bytes, CharsetUtil.UTF_8);
		this.dealTime3 = StringUtils.trim(this.dealTime3);
	}

	public void setProcessStatus3(byte[] bytes) {
		this.processStatus3 = new String(bytes, CharsetUtil.UTF_8);
		this.processStatus3 = StringUtils.trim(this.processStatus3);
	}

	public void setLocateGubun3(byte[] bytes) {
		this.locateGubun3 = new String(bytes, CharsetUtil.UTF_8);
		this.locateGubun3 = StringUtils.trim(this.locateGubun3);
	}

	public void setDealMethod3(byte[] bytes) {
		this.dealMethod3 = new String(bytes, CharsetUtil.UTF_8);
		this.dealMethod3 = StringUtils.trim(this.dealMethod3);
	}

	public void setDepositBankCode3(byte[] bytes) {
		this.depositBankCode3 = new String(bytes, CharsetUtil.UTF_8);
		this.depositBankCode3 = StringUtils.trim(this.depositBankCode3);
	}

	public void setDepositAccountNo3(byte[] bytes) {
		this.depositAccountNo3 = new String(bytes, CharsetUtil.UTF_8);
		this.depositAccountNo3 = StringUtils.trim(this.depositAccountNo3);
	}

	public void setWithdrawBankCode3(byte[] bytes) {
		this.withdrawBankCode3 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawBankCode3 = StringUtils.trim(this.withdrawBankCode3);
	}

	public void setWithdrawAccountNo3(byte[] bytes) {
		this.withdrawAccountNo3 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawAccountNo3 = StringUtils.trim(this.withdrawAccountNo3);
	}

	public void setDealAmt3(byte[] bytes) {
		this.dealAmt3 = new String(bytes, CharsetUtil.UTF_8);
		this.dealAmt3 = StringUtils.trim(this.dealAmt3);
	}

	public void setDealSeqNo4(byte[] bytes) {
		this.dealSeqNo4 = new String(bytes, CharsetUtil.UTF_8);
		this.dealSeqNo4 = StringUtils.trim(this.dealSeqNo4);
	}

	public void setDealTime4(byte[] bytes) {
		this.dealTime4 = new String(bytes, CharsetUtil.UTF_8);
		this.dealTime4 = StringUtils.trim(this.dealTime4);
	}

	public void setProcessStatus4(byte[] bytes) {
		this.processStatus4 = new String(bytes, CharsetUtil.UTF_8);
		this.processStatus4 = StringUtils.trim(this.processStatus4);
	}

	public void setLocateGubun4(byte[] bytes) {
		this.locateGubun4 = new String(bytes, CharsetUtil.UTF_8);
		this.locateGubun4 = StringUtils.trim(this.locateGubun4);
	}

	public void setDealMethod4(byte[] bytes) {
		this.dealMethod4 = new String(bytes, CharsetUtil.UTF_8);
		this.dealMethod4 = StringUtils.trim(this.dealMethod4);
	}

	public void setDepositBankCode4(byte[] bytes) {
		this.depositBankCode4 = new String(bytes, CharsetUtil.UTF_8);
		this.depositBankCode4 = StringUtils.trim(this.depositBankCode4);
	}

	public void setDepositAccountNo4(byte[] bytes) {
		this.depositAccountNo4 = new String(bytes, CharsetUtil.UTF_8);
		this.depositAccountNo4 = StringUtils.trim(this.depositAccountNo4);
	}

	public void setWithdrawBankCode4(byte[] bytes) {
		this.withdrawBankCode4 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawBankCode4 = StringUtils.trim(this.withdrawBankCode4);
	}

	public void setWithdrawAccountNo4(byte[] bytes) {
		this.withdrawAccountNo4 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawAccountNo4 = StringUtils.trim(this.withdrawAccountNo4);
	}

	public void setDealAmt4(byte[] bytes) {
		this.dealAmt4 = new String(bytes, CharsetUtil.UTF_8);
		this.dealAmt4 = StringUtils.trim(this.dealAmt4);
	}

	public void setDealSeqNo5(byte[] bytes) {
		this.dealSeqNo5 = new String(bytes, CharsetUtil.UTF_8);
		this.dealSeqNo5 = StringUtils.trim(this.dealSeqNo5);
	}

	public void setDealTime5(byte[] bytes) {
		this.dealTime5 = new String(bytes, CharsetUtil.UTF_8);
		this.dealTime5 = StringUtils.trim(this.dealTime5);
	}

	public void setProcessStatus5(byte[] bytes) {
		this.processStatus5 = new String(bytes, CharsetUtil.UTF_8);
		this.processStatus5 = StringUtils.trim(this.processStatus5);
	}

	public void setLocateGubun5(byte[] bytes) {
		this.locateGubun5 = new String(bytes, CharsetUtil.UTF_8);
		this.locateGubun5 = StringUtils.trim(this.locateGubun5);
	}

	public void setDealMethod5(byte[] bytes) {
		this.dealMethod5 = new String(bytes, CharsetUtil.UTF_8);
		this.dealMethod5 = StringUtils.trim(this.dealMethod5);
	}

	public void setDepositBankCode5(byte[] bytes) {
		this.depositBankCode5 = new String(bytes, CharsetUtil.UTF_8);
		this.depositBankCode5 = StringUtils.trim(this.depositBankCode5);
	}

	public void setDepositAccountNo5(byte[] bytes) {
		this.depositAccountNo5 = new String(bytes, CharsetUtil.UTF_8);
		this.depositAccountNo5 = StringUtils.trim(this.depositAccountNo5);
	}

	public void setWithdrawBankCode5(byte[] bytes) {
		this.withdrawBankCode5 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawBankCode5 = StringUtils.trim(this.withdrawBankCode5);
	}

	public void setWithdrawAccountNo5(byte[] bytes) {
		this.withdrawAccountNo5 = new String(bytes, CharsetUtil.UTF_8);
		this.withdrawAccountNo5 = StringUtils.trim(this.withdrawAccountNo5);
	}

	public void setDealAmt5(byte[] bytes) {
		this.dealAmt5 = new String(bytes, CharsetUtil.UTF_8);
		this.dealAmt5 = StringUtils.trim(this.dealAmt5);
	}

	public void setFiller(byte[] bytes) {
		this.filler = new String(bytes, CharsetUtil.UTF_8);
		this.filler = StringUtils.trim(this.filler);
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SocketMsgToStringStyle.STYLE);
    }
}
