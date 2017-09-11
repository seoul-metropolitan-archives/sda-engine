package com.bgf.shbank.domain.mng.equip.sh05001110;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class Sh05001110VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String overhaulDate;

	private String cornerName;

	private String terminalInsideCleaning;

	private String terminalCleaning;

	private String intercomCleaning;

	private String floarCleaning;

	private String garbagecanCleaning;

	private String glassCleanConfirm;

	private String portfolioPaper;

	private String jrnlPaper;

	private String bnkbRibbon;

	private String noticeguide;

	private String etcNotice;

	private String intercom;

	private String dvr;

	private String fluorescentLight;

	private String cooler;

	private String heater;

	private String boothOverhaul;

	private String checkResidualQuantity;

	private String rtrvlBox;

	private String billboardLighting;

	private String unusl;

	private String reportEmp;

	private String officeName;

	private String takePhotoEnable;


    public static Sh05001110VO of(Sh05001110 sh05001110) {
        Sh05001110VO sh05001110VO = ModelMapperUtils.map(sh05001110, Sh05001110VO.class);
        return sh05001110VO;
    }

    public static List<Sh05001110VO> of(List<Sh05001110> sh05001110List) {
        return sh05001110List.stream().map(sh05001110 -> of(sh05001110)).collect(toList());
    }

    public static List<Sh05001110VO> of(Page<Sh05001110> sh05001110Page) {
        return sh05001110Page.getContent().stream().map(sh05001110 -> of(sh05001110)).collect(toList());
    }
}