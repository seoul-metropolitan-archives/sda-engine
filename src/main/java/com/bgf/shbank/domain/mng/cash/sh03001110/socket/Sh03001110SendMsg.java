package com.bgf.shbank.domain.mng.cash.sh03001110.socket;

import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by james on 17. 1. 24.
 */
@Getter
@NoArgsConstructor
public class Sh03001110SendMsg {

	/* 지점코드 [N4] */
	private byte[] branchCode;

	/* 단말번호 [N4] */
	private byte[] terminalNo;

	/* 현금출금액 [N15] */
	private byte[] cashWithdrawAmt = StringUtils.leftPad("", 15, '0').getBytes(CharsetUtil.UTF_8);

	/* 현금입금액 [N15] */
	private byte[] cashDepositAmt = StringUtils.leftPad("", 15, '0').getBytes(CharsetUtil.UTF_8);

	/* 현금잔액 [N15] */
	private byte[] cashAmt = StringUtils.leftPad("", 15, '0').getBytes(CharsetUtil.UTF_8);

	/* 수표출금액 [N15] */
	private byte[] checkWithdrawAmt = StringUtils.leftPad("", 15, '0').getBytes(CharsetUtil.UTF_8);

	/* 수표입금액 [N15] */
	private byte[] checkDepositAmt = StringUtils.leftPad("", 15, '0').getBytes(CharsetUtil.UTF_8);

	/* 수표지급가능매수 [N4] */
	private byte[] checkGiveEnableCount = StringUtils.leftPad("", 4, '0').getBytes(CharsetUtil.UTF_8);

	/* 5만원권지급가능매수 [N5] */
	private byte[] cash50kGiveEnableCount = StringUtils.leftPad("", 5, '0').getBytes(CharsetUtil.UTF_8);

	/* FILLER [AN308] */
	private byte[] filler = StringUtils.rightPad("", 308, ' ').getBytes(CharsetUtil.UTF_8);


	public void setBranchCode(String branchCode) {
		branchCode = StringUtils.leftPad(String.valueOf(branchCode), 4, '0');
		this.branchCode = branchCode.getBytes(CharsetUtil.UTF_8);
	}

	public void setTerminalNo(String terminalNo) {
		terminalNo = StringUtils.leftPad(String.valueOf(terminalNo), 4, '0');
		this.terminalNo = terminalNo.getBytes(CharsetUtil.UTF_8);
	}

}
