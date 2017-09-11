package com.bgf.shbank.domain.mng.cash.sh03001160;

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
public class Sh03001160VO extends BaseVO {

	private String txId;

	private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String addCashSendingSeqNo;

	private String cashSendingDate;

	private String billingExpectDate;

	private String addCashSendingAmt;

	private String mngOffice;

	private String closeGubun;

	private String cashSendingStndDate;

	private String addCash50kSendingAmt;

	private String addCashSendingSumAmt;

	private String stextSendGubun;

    public static Sh03001160VO of(Sh03001160 sh03001160) {
		BoundMapperFacade<Sh03001160, Sh03001160VO> mapper =
				ModelMapperUtils.getMapper("Sh03001160", Sh03001160VO.class.getPackage().getName());
		return mapper.map(sh03001160);
    }

    public static List<Sh03001160VO> of(List<Sh03001160> sh03001160List) {
        return sh03001160List.stream().map(sh03001160 -> of(sh03001160)).collect(toList());
    }

    public static List<Sh03001160VO> of(Page<Sh03001160> sh03001160Page) {
        return sh03001160Page.getContent().stream().map(sh03001160 -> of(sh03001160)).collect(toList());
    }
}