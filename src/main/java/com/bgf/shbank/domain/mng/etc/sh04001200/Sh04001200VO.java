package com.bgf.shbank.domain.mng.etc.sh04001200;

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
public class Sh04001200VO extends BaseVO {

	private String txId;

	private String jisaCode;
    private String jisaCodeName;

	private String branchCode;
    private String branchCodeName;

	private String cornerCode;

	private String corpCode;
    private String corpCodeName;

	private String inoutDatetime;

	private String inoutGubun;
    private String inoutGubunName;

    private String taskGubun;
    private String taskGubunName;

	private String authEmpNo;


    public static Sh04001200VO of(Sh04001200 sh04001200) {
        BoundMapperFacade<Sh04001200, Sh04001200VO> mapper =
                ModelMapperUtils.getMapper("Sh04001200", Sh04001200VO.class.getPackage().getName());
        return mapper.map(sh04001200);
    }

    public static List<Sh04001200VO> of(List<Sh04001200> sh04001200List) {
        return sh04001200List.stream().map(sh04001200 -> of(sh04001200)).collect(toList());
    }

    public static List<Sh04001200VO> of(Page<Sh04001200> sh04001200Page) {
        return sh04001200Page.getContent().stream().map(sh04001200 -> of(sh04001200)).collect(toList());
    }
}