package com.bgf.shbank.domain.mng.equip.sh05001130;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class Sh05001130VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String overhaulDate;

	private String cornerName;

	private String terminalFrontGlass;

	private String terminalFront;

	private String terminalCracks;

	private String mornitor;

	private String bnkbPartEntranceCracks;

	private String cardPartEntranceCracks;

	private String intercom;

	private String boardCradle;

	private String branchFloar;

	private String insideWall;

	private String outsideWall;

	private String branchGlass;

	private String ceiling;

	private String fluorescentLightGlass;

	private String stickingBillboard;

	private String garbagecan;

	private String coolerHeater;

	private String branchPeriphery;

	private String unusl;

	private String overhaulEmp;

	private String officeName;

	private String takePhotoEnable;

	private String transmitRoundGubun;


    public static Sh05001130VO of(Sh05001130 sh05001130) {
        Sh05001130VO sh05001130VO = ModelMapperUtils.map(sh05001130, Sh05001130VO.class);
        return sh05001130VO;
    }

    public static List<Sh05001130VO> of(List<Sh05001130> sh05001130List) {
        return sh05001130List.stream().map(sh05001130 -> of(sh05001130)).collect(toList());
    }

    public static List<Sh05001130VO> of(Page<Sh05001130> sh05001130Page) {
        return sh05001130Page.getContent().stream().map(sh05001130 -> of(sh05001130)).collect(toList());
    }
}