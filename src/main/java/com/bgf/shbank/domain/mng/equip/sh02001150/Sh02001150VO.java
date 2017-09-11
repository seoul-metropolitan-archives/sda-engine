package com.bgf.shbank.domain.mng.equip.sh02001150;

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
public class Sh02001150VO extends BaseVO {

	private String workSeqNo;

	private String terminalSeqNo;

	private String resultStextGubun;
	private String resultStextGubunName;

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String terminalNo;

	private String terminalCorpCode;
	private String terminalCorpCodeName;

	private String modelCode;
	private String modelCodeName;

	private String terminalProdNo;

	private String workCompleteEnable;
	private String workCompleteEnableName;

	private String workCompleteDate;

	private String unusl;

	private String stextSendGubun;


    public static Sh02001150VO of(Sh02001150 sh02001150) {

		BoundMapperFacade<Sh02001150, Sh02001150VO> mapper =
				ModelMapperUtils.getMapper("Sh02001150", Sh02001150VO.class.getPackage().getName());
		return mapper.map(sh02001150);
    }

    public static List<Sh02001150VO> of(List<Sh02001150> sh02001150List) {
        return sh02001150List.stream().map(sh02001150 -> of(sh02001150)).collect(toList());
    }

    public static List<Sh02001150VO> of(Page<Sh02001150> sh02001150Page) {
        return sh02001150Page.getContent().stream().map(sh02001150 -> of(sh02001150)).collect(toList());
    }
}