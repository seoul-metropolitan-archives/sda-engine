package com.bgf.shbank.domain.mng.cash.sh03001220;

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
public class Sh03001220VO extends BaseVO {

	private String txId;

	private String jisaCode;

	private String manlessBranchCode;

	private String branchCode;

	private String cornerCode;

	private String terminalNo;

	private String reqDate;

	private String reqGubun;

	private String cashTypeCode1;

	private String cashTypeCode1RecvAmt;

	private String cashTypeCode2;

	private String cashTypeCode2RecvAmt;

	private String cashTypeCode3;

	private String cashTypeCode3RecvAmt;

	private String cashTypeCode4;

	private String cashTypeCode4RecvAmt;

	private String cashTypeCode5;

	private String cashTypeCode5RecvAmt;

	private String cashSendingCarNo;

	private String chargeEmpName;

	private String chargeEmpRegno;

	private String chargeEmpPhotoUrl;
	private AX5File chargeEmpPhotoUrlFile;

	private String digitalSignUrl;
	private AX5File digitalSignUrlFile;

	private String s20GCardRecvCount;

	private String s20TCardRecvCount;

	private String s20pinkGCardRecvCount;

	private String s20pinkTCardRecvCount;

	private String loveCardRecvCount;

	private String hipointCardRecvCount;

	private String scrtyCardRecvCount;

	private String stextSendGubun;

    public static Sh03001220VO of(Sh03001220 sh03001220) {
		BoundMapperFacade<Sh03001220, Sh03001220VO> mapper =
				ModelMapperUtils.getMapper("Sh03001220", Sh03001220VO.class.getPackage().getName());
		return mapper.map(sh03001220);
    }

    public static List<Sh03001220VO> of(List<Sh03001220> sh03001220List) {
        return sh03001220List.stream().map(sh03001220 -> of(sh03001220)).collect(toList());
    }

    public static List<Sh03001220VO> of(Page<Sh03001220> sh03001220Page) {
        return sh03001220Page.getContent().stream().map(sh03001220 -> of(sh03001220)).collect(toList());
    }
}