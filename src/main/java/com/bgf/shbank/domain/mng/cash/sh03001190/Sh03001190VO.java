package com.bgf.shbank.domain.mng.cash.sh03001190;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class Sh03001190VO extends BaseVO {

	private String txId;

	private String jisaCode;

	private String branchCode;

	private String reqDate;

	private String fundExpenseStartDate;

	private String fundExpenseEndDate;

	private String fundExpenseTerm;

	private String nextDayCashSendingAmt;

	private String nowDayAddCashSendingAmt;

	private String nowDayRtrvlAmt;

	private String nowDayLackAmt;

	private String thisDayRtrvlExcpectAmt;

	private String nextDayRtrvlExcpectAmt;

	private String nextDayBillingAmt;

	private String totalStockAmt;

	private String beforeBdateStockAmt;

	private String beforeBdateRecvAmt;

	private String beforeBdateCashSendingAmt;

	private String beforeBdateGiveAmt;

	private String beforeBdateDepositAmt;

	private String mngOffice;

	private String thisDayAddCashSendingAmt;

	private String thisDayNoneLoadAmt;

	private String paymentOverAmt;

	private String stextSendGubun;


    public static Sh03001190VO of(Sh03001190 sh03001190) {
		BoundMapperFacade<Sh03001190, Sh03001190VO> mapper =
				ModelMapperUtils.getMapper("Sh03001190", Sh03001190VO.class.getPackage().getName());
		return mapper.map(sh03001190);
    }

    public static List<Sh03001190VO> of(List<Sh03001190> sh03001190List) {
        return sh03001190List.stream().map(sh03001190 -> of(sh03001190)).collect(toList());
    }

    public static List<Sh03001190VO> of(Page<Sh03001190> sh03001190Page) {
        return sh03001190Page.getContent().stream().map(sh03001190 -> of(sh03001190)).collect(toList());
    }
}