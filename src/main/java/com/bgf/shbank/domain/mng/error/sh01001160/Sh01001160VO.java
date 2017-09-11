package com.bgf.shbank.domain.mng.error.sh01001160;

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
public class Sh01001160VO extends BaseVO {

    private String jisaCode;

    private String branchCode;

    private String cornerCode;

    private String terminalNo;

    private String errorDatetime;

    private String calleeReqSeqNo;

    private String calleeReqChasu;

    private String calleeReqGubunCode;

    private String calleeChasuGubun;

    private String cancleReqDatetime;

    private String calleeCancleReasonCode;

    private String unusl;

    private String crtNo;

    private String calleeReqReasonCode;

    private String terminalErrorCode1;

    private String terminalErrorCode2;

    private String totalClassifyCode;

    private String securityCorp;

    private String selfCalleeGubun;


    public static Sh01001160VO of(Sh01001160 sh01001160) {
        BoundMapperFacade<Sh01001160, Sh01001160VO> mapper =
                ModelMapperUtils.getMapper("Sh01001160", Sh01001160VO.class.getPackage().getName());
        return mapper.map(sh01001160);
    }

    public static List<Sh01001160VO> of(List<Sh01001160> sh01001160List) {
        return sh01001160List.stream().map(sh01001160 -> of(sh01001160)).collect(toList());
    }

    public static List<Sh01001160VO> of(Page<Sh01001160> sh01001160Page) {
        return sh01001160Page.getContent().stream().map(sh01001160 -> of(sh01001160)).collect(toList());
    }
}