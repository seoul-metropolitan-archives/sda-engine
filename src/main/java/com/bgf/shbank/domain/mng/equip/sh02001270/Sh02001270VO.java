package com.bgf.shbank.domain.mng.equip.sh02001270;

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
public class Sh02001270VO extends BaseVO {

	private String workSeqNo;

	private String chasu;

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String cornerName;

	private String stextGubun;

	private String closingDate;

	private String restartGubun;

	private String restartDate;

	private String changeItemGubun;

	private String operStartTime;

	private String operEndTime;

	private String checkOper;

	private String applyDate;


    public static Sh02001270VO of(Sh02001270 sh02001270) {

		BoundMapperFacade<Sh02001270, Sh02001270VO> mapper =
				ModelMapperUtils.getMapper("Sh02001270", Sh02001270VO.class.getPackage().getName());
		return mapper.map(sh02001270);
    }

    public static List<Sh02001270VO> of(List<Sh02001270> sh02001270List) {
        return sh02001270List.stream().map(sh02001270 -> of(sh02001270)).collect(toList());
    }

    public static List<Sh02001270VO> of(Page<Sh02001270> sh02001270Page) {
        return sh02001270Page.getContent().stream().map(sh02001270 -> of(sh02001270)).collect(toList());
    }
}