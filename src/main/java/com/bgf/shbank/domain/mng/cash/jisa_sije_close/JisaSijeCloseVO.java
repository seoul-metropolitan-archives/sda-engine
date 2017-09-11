package com.bgf.shbank.domain.mng.cash.jisa_sije_close;

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
public class JisaSijeCloseVO extends BaseVO {

	private String txId;

	private String closeDate;

	private String jisaCode;

	private String prevDayReserveSije;

	private String thisDayCashDepositAmt;

	private String jisaToShinhanSendAmt;

	private String closeAmt;

	private String unCheckAmt;

	private String sijeMistakeAmt;

	private String cashSendingAmt;

	private String addCashSendingAmt;

	private String jisaSafeAmt;

	private String memoContent;

	private String userNm;


    public static JisaSijeCloseVO of(JisaSijeClose jisaSijeClose) {
		BoundMapperFacade<JisaSijeClose, JisaSijeCloseVO> mapper =
				ModelMapperUtils.getMapper("JisaSijeClose", JisaSijeCloseVO.class.getPackage().getName());
        return mapper.map(jisaSijeClose);
    }

    public static List<JisaSijeCloseVO> of(List<JisaSijeClose> jisaSijeCloseList) {
        return jisaSijeCloseList.stream().map(jisaSijeClose -> of(jisaSijeClose)).collect(toList());
    }

    public static List<JisaSijeCloseVO> of(Page<JisaSijeClose> jisaSijeClosePage) {
        return jisaSijeClosePage.getContent().stream().map(jisaSijeClose -> of(jisaSijeClose)).collect(toList());
    }
}