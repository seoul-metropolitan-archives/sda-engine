package com.bgf.shbank.domain.mng.equip.sh02001290;

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
public class Sh02001290VO extends BaseVO {

	private String txId;

	private String billingMonth;

	private String costGubun;
	private String costGubunName;

	private String detailItemGubun;
	private String detailItemGubunName;

	private String seqNo;

	private String workDate;

	private String inspectionMonth;

	private String billingAmt;

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;
	private String branchCodeName;

	private String cornerCode;

	private String terminalNo;

	private String cornerName;

	private String detailContent;

	private String stextSendGubun;


    public static Sh02001290VO of(Sh02001290 sh02001290) {
		BoundMapperFacade<Sh02001290, Sh02001290VO> mapper =
				ModelMapperUtils.getMapper("Sh02001290", Sh02001290VO.class.getPackage().getName());
		return mapper.map(sh02001290);
    }

    public static List<Sh02001290VO> of(List<Sh02001290> sh02001290List) {
        return sh02001290List.stream().map(sh02001290 -> of(sh02001290)).collect(toList());
    }

    public static List<Sh02001290VO> of(Page<Sh02001290> sh02001290Page) {
        return sh02001290Page.getContent().stream().map(sh02001290 -> of(sh02001290)).collect(toList());
    }
}