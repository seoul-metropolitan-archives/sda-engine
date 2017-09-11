package com.bgf.shbank.domain.mng.etc.sh04001170;

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
public class Sh04001170VO extends BaseVO {

	private String txId;

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;
	private String branchCodeName;

	private String terminalNo;

	private String infoGubun;

	private String partitionCount;

	private String driveGubun1;

	private String totalLimit1;

	private String usage1;

	private String driveGubun2;

	private String totalLimit2;

	private String usage2;

	private String driveGubun3;

	private String totalLimit3;

	private String usage3;

	private String driveGubun4;

	private String totalLimit4;

	private String usage4;

	private String driveGubun5;

	private String totalLimit5;

	private String usage5;

	private String driveGubun6;

	private String totalLimit6;

	private String usage6;

	private String content;


    public static Sh04001170VO of(Sh04001170 sh04001170) {
		BoundMapperFacade<Sh04001170, Sh04001170VO> mapper =
				ModelMapperUtils.getMapper("Sh04001170", Sh04001170VO.class.getPackage().getName());
		return mapper.map(sh04001170);
    }

    public static List<Sh04001170VO> of(List<Sh04001170> sh04001170List) {
        return sh04001170List.stream().map(sh04001170 -> of(sh04001170)).collect(toList());
    }

    public static List<Sh04001170VO> of(Page<Sh04001170> sh04001170Page) {
        return sh04001170Page.getContent().stream().map(sh04001170 -> of(sh04001170)).collect(toList());
    }
}