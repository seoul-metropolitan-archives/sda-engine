package com.bgf.shbank.domain.mng.etc.sh04001130;

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
public class Sh04001130VO extends BaseVO {

	private String serviceFeeCalcYearMonth;

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;
	private String branchCodeName;

	private String cornerCode;

	private String cornerName;

	private String branchGubunCode;
	private String branchGubunCodeName;

	private String operTimeGubunCode;
	private String operTimeGubunCodeName;

	private String terminalCount;

	private String checkOperEnable;
	private String checkOperEnableName;

	private String basicServiceFee;

	private String terminalAddServiceFee;

	private String timeAddServiceFee;

	private String checkAddServiceFee;

	private String operDateCount;

	private String monthServiceFee;

	private String penalty;

	private String totalGiveServiceFee;

	private String note;

	private String stextSendGubun;

    public static Sh04001130VO of(Sh04001130 sh04001130) {
		BoundMapperFacade<Sh04001130, Sh04001130VO> mapper =
				ModelMapperUtils.getMapper("Sh04001130", Sh04001130VO.class.getPackage().getName());
		return mapper.map(sh04001130);
    }

    public static List<Sh04001130VO> of(List<Sh04001130> sh04001130List) {
        return sh04001130List.stream().map(sh04001130 -> of(sh04001130)).collect(toList());
    }

    public static List<Sh04001130VO> of(Page<Sh04001130> sh04001130Page) {
        return sh04001130Page.getContent().stream().map(sh04001130 -> of(sh04001130)).collect(toList());
    }
}