package com.bgf.shbank.domain.mng.error.sh01001140;

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
public class Sh01001140VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String terminalNo;

	private String errorDatetime;

	private String calleeReqSeqNo;

	private String calleeReqChasu;

	private String calleeReqGubun;

	private String calleeChasuGubun;

	private String cornerArrivalDatetime;

	private String arrivalCorpCode;

	private String unusl;

	private String crtNo;


    public static Sh01001140VO of(Sh01001140 sh01001140) {
		BoundMapperFacade<Sh01001140, Sh01001140VO> mapper =
				ModelMapperUtils.getMapper("Sh01001140", Sh01001140VO.class.getPackage().getName());
		return mapper.map(sh01001140);
    }

    public static List<Sh01001140VO> of(List<Sh01001140> sh01001140List) {
        return sh01001140List.stream().map(sh01001140 -> of(sh01001140)).collect(toList());
    }

    public static List<Sh01001140VO> of(Page<Sh01001140> sh01001140Page) {
        return sh01001140Page.getContent().stream().map(sh01001140 -> of(sh01001140)).collect(toList());
    }
}