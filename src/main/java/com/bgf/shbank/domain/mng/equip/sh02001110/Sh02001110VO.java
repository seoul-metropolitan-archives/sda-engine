package com.bgf.shbank.domain.mng.equip.sh02001110;

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
public class Sh02001110VO extends BaseVO {

	private String workSeqNo;

	private String changeChasu;

	private String stextGubun;
	private String stextGubunName;

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;

	private String branchName;

	private String cornerCode;

	private String cornerName;

	private String placeGubun;
	private String placeGubunName;

	private String branchGubun;
	private String branchGubunName;

	private String specialStyleGubun;
	private String specialStyleGubunName;

	private String operTimeGubun;
	private String operTimeGubunName;

	private String operStartTime;

	private String operEndTime;

	private String checkOperEnable;
	private String checkOperEnableName;

	private String serviceFee;

	private String securityCorpCode;
	private String securityCorpCodeName;

	private String facHireEnable;
	private String facHireEnableName;

	private String addr;

	private String unusl;

	private String operDay;


    public static Sh02001110VO of(Sh02001110 sh02001110) {
		BoundMapperFacade<Sh02001110, Sh02001110VO> mapper =
				ModelMapperUtils.getMapper("Sh02001110", Sh02001110VO.class.getPackage().getName());
		return mapper.map(sh02001110);
    }

    public static List<Sh02001110VO> of(List<Sh02001110> sh02001110List) {
        return sh02001110List.stream().map(sh02001110 -> of(sh02001110)).collect(toList());
    }

    public static List<Sh02001110VO> of(Page<Sh02001110> sh02001110Page) {
        return sh02001110Page.getContent().stream().map(sh02001110 -> of(sh02001110)).collect(toList());
    }
}