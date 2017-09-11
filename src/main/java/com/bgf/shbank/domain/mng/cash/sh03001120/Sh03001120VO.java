package com.bgf.shbank.domain.mng.cash.sh03001120;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by tw.jang on 17. 1. 24.
 */
@Getter
@Setter
public class Sh03001120VO extends BaseVO {

	private String filter;

	private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String referDate;

	private String referStatementNo;

	private String referStartTime;

	private String referEndTime;

	private String dealSeqNo;

	private String dealTime;

	private String processStatus;

	private String locateGubun;

	private String dealMethod;

	private String depositBankCode;

	private String depositBankName;

	/* 입금계좌번호 [AN16] */
	private String depositAccountNo;

	/* 출금은행코드 [AN3] */
	private String withdrawBankCode;

	private String withdrawBankName;

	/* 출금계좌번호 [AN16] */
	private String withdrawAccountNo;

	/* 거래금액 [AN12] */
	private String dealAmt;

	private String stextSendGubun;


	public static Sh03001120VO of(Sh03001120 Sh03001120) {
		BoundMapperFacade<Sh03001120, Sh03001120VO> mapper =
				ModelMapperUtils.getMapper("Sh03001120", Sh03001120VO.class.getPackage().getName());
		return mapper.map(Sh03001120);
	}

	public static List<Sh03001120VO> of(List<Sh03001120> Sh03001120List) {
		return Sh03001120List.stream().map(Sh03001120 -> of(Sh03001120)).collect(toList());
	}

	public static List<Sh03001120VO> of(Page<Sh03001120> Sh03001120Page) {
		return Sh03001120Page.getContent().stream().map(Sh03001120 -> of(Sh03001120)).collect(toList());
	}

}