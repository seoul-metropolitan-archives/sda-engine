package com.bgf.shbank.domain.mng.error.sh01001190;

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
public class Sh01001190VO extends BaseVO {

	private String handleDatetime;

	private String mngStndDate;

	private String mngStndCritic;

	private String errorCount;

	private String jisaCode;

	private String branchCode;

	private String terminalNo;

	private String cornerCode;

	private String modelCode;

	private String cornerName;

	private String handleEmpName;

	private String memoContent;

	private String calleeReqDate;

	private String calleeSeqNo;

	private String errorMethod;



	public static Sh01001190VO of(Sh01001190 sh01001190) {
		BoundMapperFacade<Sh01001190, Sh01001190VO> mapper =
				ModelMapperUtils.getMapper("Sh01001190", Sh01001190VO.class.getPackage().getName());
		return mapper.map(sh01001190);
	}
    public static List<Sh01001190VO> of(List<Sh01001190> sh01001190List) {
        return sh01001190List.stream().map(sh01001190 -> of(sh01001190)).collect(toList());
    }

    public static List<Sh01001190VO> of(Page<Sh01001190> sh01001190Page) {
        return sh01001190Page.getContent().stream().map(sh01001190 -> of(sh01001190)).collect(toList());
    }
}