package com.bgf.shbank.domain.mng.cash.sh03001150;

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
public class Sh03001150VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String noneProcessAmt;

	private String noneProcessSeqNo;

	private String dealDate;

	private String statementNo;

	private String dealBank;

	private String cardAccountNo;

	private String dealType;

	private String dealAmt;

	private String managerName;

	private String customerName;

	private String customerTelno;

	private String mngOffice;

	private String unusl;

	private String processDate;

	private String sendCommission;

	private String rtnCommission;

	private String stextSendGubun;


    public static Sh03001150VO of(Sh03001150 sh03001150) {

		BoundMapperFacade<Sh03001150, Sh03001150VO> mapper =
				ModelMapperUtils.getMapper("Sh03001150", Sh03001150VO.class.getPackage().getName());
		return mapper.map(sh03001150);
    }

    public static List<Sh03001150VO> of(List<Sh03001150> sh03001150List) {
        return sh03001150List.stream().map(sh03001150 -> of(sh03001150)).collect(toList());
    }

    public static List<Sh03001150VO> of(Page<Sh03001150> sh03001150Page) {
        return sh03001150Page.getContent().stream().map(sh03001150 -> of(sh03001150)).collect(toList());
    }
}