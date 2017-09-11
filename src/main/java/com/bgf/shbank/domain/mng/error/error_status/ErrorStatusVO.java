package com.bgf.shbank.domain.mng.error.error_status;

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
public class ErrorStatusVO extends BaseVO {
    private String jisaCode;

    private String branchCode;

    private String cornerCode;

    private String terminalNo;

    private String calleeGubun;

    private String errorDatetime;

    private String errorDate;

    private String errorTime;

    private String calleeReqDatetime;

    private String calleeReqDate;

    private String calleeReqTime;

    private String calleePlanDatetime;

    private String calleePlanDate;

    private String calleePlanTime;

    private String arrivalPlanDatetime;

    private String arrivalPlanDate;

    private String arrivalPlanTime;

    private String cornerArrivalDatetime;

    private String cornerArrivalDate;

    private String cornerArrivalTime;

    private String handleDatetime;

    private String handleDate;

    private String handleTime;

    private String cancleReqDatetime;

    private String cancleReqDate;

    private String cancleReqtime;

    private String calleeReqSeqNo;

    private String elapsedTime;

    private String errorStatusClass;

    private String errorType;

    private int errorProcessStatus;

    private String cash10kEmptyStatus;

    private String cash50kEmptyStatus;

    private String txId;

    // 조치사항관련 Properties
    private String startDate;
    private String endDate;

    private String noticeContent;
    private String customerInfo;
    private String handleContent;
    private String lastModifyDatetime;
    private String lastModifyEmpName;
    private String stextGubun;
    private String totalClassifyCode;
    private String customerWaitEnable;
    private String pushString;

    public static ErrorStatusVO of(ErrorStatus errorStatus) {
        BoundMapperFacade<ErrorStatus, ErrorStatusVO> mapper =
                ModelMapperUtils.getMapper("ErrorStatus", ErrorStatusVO.class.getPackage().getName());
        ErrorStatusVO errorStatusVO = mapper.map(errorStatus);
        return errorStatusVO;
    }

    public static List<ErrorStatusVO> of(List<ErrorStatus> errorStatusList) {
        return errorStatusList.stream().map(errorStatus -> of(errorStatus)).collect(toList());
    }

    public static List<ErrorStatusVO> of(Page<ErrorStatus> errorStatusPage) {
        return errorStatusPage.getContent().stream().map(errorStatus -> of(errorStatus)).collect(toList());
    }

}