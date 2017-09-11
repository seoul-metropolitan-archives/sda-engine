package com.bgf.shbank.domain.mng.equip.sh02001180;

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
public class Sh02001180VO extends BaseVO {

	private String workSeqNo;

	private String facSeqNo;

	private String resultStextGubun;
	private String resultStextGubunName;

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String facCode;

	private String facName;

	private String assetSeqNo;

	private String adoptDate;

	private String workCompleteEnable;
	private String workCompleteEnableName;

	private String workCompleteDate;

	private String unusl;

	private String stextSendGubun;


    public static Sh02001180VO of(Sh02001180 sh02001180) {

		BoundMapperFacade<Sh02001180, Sh02001180VO> mapper =
				ModelMapperUtils.getMapper("Sh02001180", Sh02001180VO.class.getPackage().getName());
		return mapper.map(sh02001180);
    }

    public static List<Sh02001180VO> of(List<Sh02001180> sh02001180List) {
        return sh02001180List.stream().map(sh02001180 -> of(sh02001180)).collect(toList());
    }

    public static List<Sh02001180VO> of(Page<Sh02001180> sh02001180Page) {
        return sh02001180Page.getContent().stream().map(sh02001180 -> of(sh02001180)).collect(toList());
    }
}