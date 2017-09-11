package com.bgf.shbank.domain.mng.error.sh01001230;

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
public class Sh01001230VO extends BaseVO {

    private String jisaCode;

    private String branchCode;

    private String cornerCode;

    private String terminalNo;

    private String stextChasu;

    private String handleOccurDatetime;

    private String handleOccurDate;

    private String handleOccurTime;

    private String handleSeqNo;

    private String handleNoticeDatetime;

    private String handleNoticeDate;

    private String handleNoticeTime;

    private String handleNoticeEmpName;

    private String handleNoticeEmpTelno;

    private String handleReqDesc;

    private String handleStatus;

    private String smsEnable;

    private String customerName;

    private String customerTelno;

    private String errorType;

    private String accountNo;

    private String dealSeqNo;

    private String dealAmount;

    private String handleDatetime;

    private String handleDate;

    private String handleTime;

    private String handleEmpName;

    private String handleEmpTelno;

    private String handleDesc;

    private String stextSendGubun;

    public static Sh01001230VO of(Sh01001230 sh01001230) {
        BoundMapperFacade<Sh01001230, Sh01001230VO> mapper =
                ModelMapperUtils.getMapper("Sh01001230", Sh01001230VO.class.getPackage().getName());
        return mapper.map(sh01001230);
    }

    public static List<Sh01001230VO> of(List<Sh01001230> sh01001230List) {
        return sh01001230List.stream().map(sh01001230 -> of(sh01001230)).collect(toList());
    }

    public static List<Sh01001230VO> of(Page<Sh01001230> sh01001230Page) {
        return sh01001230Page.getContent().stream().map(sh01001230 -> of(sh01001230)).collect(toList());
    }
}