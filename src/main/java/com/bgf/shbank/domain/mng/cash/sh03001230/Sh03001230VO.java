package com.bgf.shbank.domain.mng.cash.sh03001230;

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
public class Sh03001230VO extends BaseVO {

	private String txId;

	private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String rtnDate;

	private String confirmDate;

	private String dealDate;

	private String rtnAmt;

	private String content;

	private String chargeCenter;

	private String note;

	private String stextSendGubun;

    public static Sh03001230VO of(Sh03001230 sh03001230) {
		BoundMapperFacade<Sh03001230, Sh03001230VO> mapper =
				ModelMapperUtils.getMapper("Sh03001230", Sh03001230VO.class.getPackage().getName());
		return mapper.map(sh03001230);
    }

    public static List<Sh03001230VO> of(List<Sh03001230> sh03001230List) {
        return sh03001230List.stream().map(sh03001230 -> of(sh03001230)).collect(toList());
    }

    public static List<Sh03001230VO> of(Page<Sh03001230> sh03001230Page) {
        return sh03001230Page.getContent().stream().map(sh03001230 -> of(sh03001230)).collect(toList());
    }
}