package com.bgf.shbank.domain.mng.cash.sh03001180;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Sh03001180ExcelVO extends BaseVO {

    private String jisaCode;

    private String userName;

    private String cashSendingDate;

	private String terminalNo;

	private String modelName;

	private String cornerName;

    private String weekCashSendingEnable;

	private Long cashAmt;

	private Long cash50kGiveAmt;

	private Long predictionCashSendingAmt;

	private Long predictionCash50kSendingAmt;

	private Long cashSendingAmt;

    private Long cash50kSendingAmt;

    private Long addCashSendingAmt;

    private Long addCash50kSendingAmt;

	private Long rtrvlAmt;
}