package com.bgf.shbank.domain.mng.cash.sh03001110.socket;

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
public class Sh03001110RecvMsg {

	/* 지점코드 [N4] */
	private String branchCode;

	/* 단말번호 [N4] */
	private String terminalNo;

	/* 현금출금액 [N15] */
	private String cashWithdrawAmt;

	/* 현금입금액 [N15] */
	private String cashDepositAmt;

	/* 현금잔액 [N15] */
	private String cashAmt;

	/* 수표출금액 [N15] */
	private String checkWithdrawAmt;

	/* 수표입금액 [N15] */
	private String checkDepositAmt;

	/* 수표지급가능매수 [N4] */
	private String checkGiveEnableCount;

	/* 5만원권지급가능매수 [N5] */
	private String cash50kGiveEnableCount;

	/* FILLER [AN308] */
	private String filler;


	public void setBranchCode(byte[] bytes) {
		this.branchCode = new String(bytes, CharsetUtil.UTF_8);
		this.branchCode = StringUtils.trim(this.branchCode);
	}

	public void setTerminalNo(byte[] bytes) {
		this.terminalNo = new String(bytes, CharsetUtil.UTF_8);
		this.terminalNo = StringUtils.trim(this.terminalNo);
	}

	public void setCashWithdrawAmt(byte[] bytes) {
		this.cashWithdrawAmt = new String(bytes, CharsetUtil.UTF_8);
		this.cashWithdrawAmt = Integer.valueOf(this.cashWithdrawAmt).toString();
	}

	public void setCashDepositAmt(byte[] bytes) {
		this.cashDepositAmt = new String(bytes, CharsetUtil.UTF_8);
		this.cashDepositAmt = Integer.valueOf(this.cashDepositAmt).toString();
	}

	public void setCashAmt(byte[] bytes) {
		this.cashAmt = new String(bytes, CharsetUtil.UTF_8);
		this.cashAmt = Integer.valueOf(this.cashAmt).toString();
	}

	public void setCheckWithdrawAmt(byte[] bytes) {
		this.checkWithdrawAmt = new String(bytes, CharsetUtil.UTF_8);
		this.checkWithdrawAmt = Integer.valueOf(this.checkWithdrawAmt).toString();
	}

	public void setCheckDepositAmt(byte[] bytes) {
		this.checkDepositAmt = new String(bytes, CharsetUtil.UTF_8);
		this.checkDepositAmt = Integer.valueOf(this.checkDepositAmt).toString();
	}

	public void setCheckGiveEnableCount(byte[] bytes) {
		this.checkGiveEnableCount = new String(bytes, CharsetUtil.UTF_8);
		this.checkGiveEnableCount = StringUtils.trim(this.checkGiveEnableCount);
	}

	public void setCash50kGiveEnableCount(byte[] bytes) {
		this.cash50kGiveEnableCount = new String(bytes, CharsetUtil.UTF_8);
		this.cash50kGiveEnableCount = StringUtils.trim(this.cash50kGiveEnableCount);
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
