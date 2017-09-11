package com.bgf.shbank.domain.mng.error.sh01001180;

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
public class Sh01001180VO extends BaseVO {

    private String errorDatetime;

    private String errorDate;

    private String errorTime;

    private String mngStndDate;

    private String mngStndCritic;

    private String errorCount;

    private String jisaCode;

    private String branchCode;

    private String terminalNo;

    private String cornerCode;

    private String modelCode;

    private String cornerName;

    private String unusl;

    private String calleeSeqNo;

    private String errorMethod;

    private String errorType;

    private String totalClassifyCode;

    private String terminalError1;

    private String terminalError2;

    private String handleDatetime;

    private String handleDate;

    private String handleTime;

    private String handleEmpName;

    private String memoContent;

    private String calleeReqDate;

    private String stextSendGubun;


    public static Sh01001180VO of(Sh01001180 sh01001180) {
        BoundMapperFacade<Sh01001180, Sh01001180VO> mapper =
                ModelMapperUtils.getMapper("Sh01001180", Sh01001180VO.class.getPackage().getName());
        return mapper.map(sh01001180);
    }

    public static List<Sh01001180VO> of(List<Sh01001180> sh01001180List) {
        return sh01001180List.stream().map(sh01001180 -> of(sh01001180)).collect(toList());
    }

    public static List<Sh01001180VO> of(Page<Sh01001180> sh01001180Page) {
        return sh01001180Page.getContent().stream().map(sh01001180 -> of(sh01001180)).collect(toList());
    }
}