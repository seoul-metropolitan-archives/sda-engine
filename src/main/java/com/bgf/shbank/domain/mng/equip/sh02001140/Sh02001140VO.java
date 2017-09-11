package com.bgf.shbank.domain.mng.equip.sh02001140;

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
public class Sh02001140VO extends BaseVO {

	private String workSeqNo;

	private String terminalSeqNo;

	private String changeChasu;

	private String stextGubun;
	private String stextGubunName;

	private String nouseJisaCode;
	private String nouseJisaCodeName;

	private String nouseBranchCode;
	private String nouseBranchCodeName;

	private String nouseCornerCode;
	private String nouseCornerCodeName;

	private String nouseTerminalNo;

	private String terminalCorpCode;
	private String terminalCorpCodeName;

	private String modelCode;
	private String modelCodeName;

	private String hireTerminalEnable;
	private String hireTerminalEnableName;

	private String nouseGubun;
	private String nouseGubunName;

	private String workPlanDatetime;


    public static Sh02001140VO of(Sh02001140 sh02001140) {

		BoundMapperFacade<Sh02001140, Sh02001140VO> mapper =
				ModelMapperUtils.getMapper("Sh02001140", Sh02001140VO.class.getPackage().getName());
		return mapper.map(sh02001140);
    }

    public static List<Sh02001140VO> of(List<Sh02001140> sh02001140List) {
        return sh02001140List.stream().map(sh02001140 -> of(sh02001140)).collect(toList());
    }

    public static List<Sh02001140VO> of(Page<Sh02001140> sh02001140Page) {
        return sh02001140Page.getContent().stream().map(sh02001140 -> of(sh02001140)).collect(toList());
    }
}