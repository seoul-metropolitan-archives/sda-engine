package com.bgf.shbank.domain.mng.equip.sh05001120;

import com.bgf.shbank.core.upload.AX5File;
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
public class Sh05001120VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String overhaulDate;

	private String cornerName;

	private String photoUrl;

	private String outsideBillboardPhoto;
	private AX5File outsideBillboardPhotoFile;

	private String gatePhoto;
	private AX5File gatePhotoFile;

	private String floarPhoto;
	private AX5File floarPhotoFile;

	private String boothLeftPhoto;
	private AX5File boothLeftPhotoFile;

	private String boothRightPhoto;
	private AX5File boothRightPhotoFile;

	private String ceilingPhoto;
	private AX5File ceilingPhotoFile;

	private String terminalTopPhoto;
	private AX5File terminalTopPhotoFile;

	private String terminalBottomPhoto;
	private AX5File terminalBottomPhotoFile;


	public static Sh05001120VO of(Sh05001120 sh05001120) {
		BoundMapperFacade<Sh05001120, Sh05001120VO> mapper =
				ModelMapperUtils.getMapper("Sh05001120", Sh05001120VO.class.getPackage().getName());
		return mapper.map(sh05001120);
	}

    public static List<Sh05001120VO> of(List<Sh05001120> sh05001120List) {
        return sh05001120List.stream().map(sh05001120 -> of(sh05001120)).collect(toList());
    }

    public static List<Sh05001120VO> of(Page<Sh05001120> sh05001120Page) {
        return sh05001120Page.getContent().stream().map(sh05001120 -> of(sh05001120)).collect(toList());
    }
}