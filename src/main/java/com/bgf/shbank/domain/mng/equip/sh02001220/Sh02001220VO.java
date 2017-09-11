package com.bgf.shbank.domain.mng.equip.sh02001220;

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
public class Sh02001220VO extends BaseVO {

	private String workSeqNo;

	private String facSeqNo;

	private String changeChasu;

	private String stextGubun;

	private String jisaCode;

	private String nouseBranchCode;

	private String nouseCornerCode;

	private String nouseFacGubunCode;

	private String nouseFacCode;

	private String nouseAssetSeqNo;

	private String nouseGubun;

	private String newBranchCode;

	private String newCornerCode;

	private String newFacGubunCode;

	private String newFacCode;

	private String newHireFacEnable;

	private String newInstallArticleGubun;

	private String newAssetSeqNo;

	private String facIpAddr;

	private String facGwAddr;

	private String facSmaskAddr;

	private String newHireFee;


	public static Sh02001220VO of(Sh02001220 sh02001220) {
		BoundMapperFacade<Sh02001220, Sh02001220VO> mapper =
				ModelMapperUtils.getMapper("Sh02001220", Sh02001220VO.class.getPackage().getName());
		return mapper.map(sh02001220);
	}

    public static List<Sh02001220VO> of(List<Sh02001220> sh02001220List) {
        return sh02001220List.stream().map(sh02001220 -> of(sh02001220)).collect(toList());
    }

    public static List<Sh02001220VO> of(Page<Sh02001220> sh02001220Page) {
        return sh02001220Page.getContent().stream().map(sh02001220 -> of(sh02001220)).collect(toList());
    }
}