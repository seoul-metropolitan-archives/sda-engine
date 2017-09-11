package com.bgf.shbank.domain.mng.equip.sh02001170;

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
public class Sh02001170VO extends BaseVO {

	private String workSeqNo;

	private String facSeqNo;

	private String changeChasu;

	private String stextGubun;
	private String stextGubunName;

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String facGubunCode;
	private String facGubunCodeName;

	private String facCode;
	private String facCodeName;

	private String hireFacEnable;
	private String hireFacEnableName;

	private String nouseGubun;
	private String nouseGubunName;

	private String assetSeqNo;


    public static Sh02001170VO of(Sh02001170 sh02001170) {
		BoundMapperFacade<Sh02001170, Sh02001170VO> mapper =
				ModelMapperUtils.getMapper("Sh02001170", Sh02001170VO.class.getPackage().getName());
		return mapper.map(sh02001170);
    }

    public static List<Sh02001170VO> of(List<Sh02001170> sh02001170List) {
        return sh02001170List.stream().map(sh02001170 -> of(sh02001170)).collect(toList());
    }

    public static List<Sh02001170VO> of(Page<Sh02001170> sh02001170Page) {
        return sh02001170Page.getContent().stream().map(sh02001170 -> of(sh02001170)).collect(toList());
    }
}