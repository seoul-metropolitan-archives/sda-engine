package com.bgf.shbank.domain.mng.equip.sh02001160;

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
public class Sh02001160VO extends BaseVO {

	private String workSeqNo;

	private String facSeqNo;

	private String changeChasu;

	private String stextGubun;
	private String stextGubunName;

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;
	private String branchCodeName;

	private String cornerCode;

	private String facGubunCode;
	private String facGubunCodeName;

	private String facCode;
	private String facCodeName;

	private String hireFacEnable;
	private String hireFacEnableName;

	private String installArticleGubun;
	private String installArticleGubunName;

	private String assetSeqNo;

	private String facIpAddr;

	private String facGwAddr;

	private String facSmaskAddr;

	private String hireFee;

	private String detailFacInfo;

	public static Sh02001160VO of(Sh02001160 sh02001160) {
		BoundMapperFacade<Sh02001160, Sh02001160VO> mapper =
				ModelMapperUtils.getMapper("Sh02001160", Sh02001160VO.class.getPackage().getName());
		return mapper.map(sh02001160);
	}

    public static List<Sh02001160VO> of(List<Sh02001160> sh02001160List) {
        return sh02001160List.stream().map(sh02001160 -> of(sh02001160)).collect(toList());
    }

    public static List<Sh02001160VO> of(Page<Sh02001160> sh02001160Page) {
        return sh02001160Page.getContent().stream().map(sh02001160 -> of(sh02001160)).collect(toList());
    }
}