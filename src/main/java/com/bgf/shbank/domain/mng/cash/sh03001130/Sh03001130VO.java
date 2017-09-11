package com.bgf.shbank.domain.mng.cash.sh03001130;

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
public class Sh03001130VO extends BaseVO {

	private String txId;

	private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String safeNo;

	private String reqDate;

	private String closeGubun;

	private String cashGiveAmt;

	private String cashDepositAmt;

	private String cashSendingAmt;

	private String rtrvlAmt;

	private String checkWithdrawAmt;

	private String checkDepositAmt;

	private String nowDayPutAmt;

	private String prevDayPutAmt;

	private String prevDayAddPutAmt;

	private String holidayAddPutAmt;

	private String thisDayAddPutAmt;

	private String atmcTerminalNowSijae;

	private String sijaeCash50k;

	private String closeDatetime;

	private String closeDate;

	private String closeTime;

	private String stextSendGubun;


    public static Sh03001130VO of(Sh03001130 sh03001130) {
		BoundMapperFacade<Sh03001130, Sh03001130VO> mapper =
				ModelMapperUtils.getMapper("Sh03001130", Sh03001130VO.class.getPackage().getName());
		return mapper.map(sh03001130);
    }

    public static List<Sh03001130VO> of(List<Sh03001130> sh03001130List) {
        return sh03001130List.stream().map(sh03001130 -> of(sh03001130)).collect(toList());
    }

    public static List<Sh03001130VO> of(Page<Sh03001130> sh03001130Page) {
        return sh03001130Page.getContent().stream().map(sh03001130 -> of(sh03001130)).collect(toList());
    }
}