package com.bgf.shbank.domain.mng.error.error_status;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ATMS_ERROR_STATUS")
@IdClass(ErrorStatus.ErrorStatusId.class)
@Alias("errorStatus")
public class ErrorStatus extends SimpleJpaModel<ErrorStatus.ErrorStatusId> {

    @Id
    @Column(name = "BRANCH_CODE", nullable = false)
    private String branchCode;

    @Id
    @Column(name = "CORNER_CODE", nullable = false)
    private String cornerCode;

    @Id
    @Column(name = "TERMINAL_NO", nullable = false)
    private String terminalNo;

    @Id
    @Column(name = "ERROR_DATETIME", nullable = false)
    private Timestamp errorDatetime;

    @Column(name = "JISA_CODE", nullable = false)
    private String jisaCode;

    @Column(name = "CALLEE_GUBUN")
    private String calleeGubun;

    @Column(name = "CALLEE_REQ_DATETIME")
    private Timestamp calleeReqDatetime;

    @Column(name = "CALLEE_PLAN_DATETIME")
    private Timestamp calleePlanDatetime;

    @Column(name = "ARRIVAL_PLAN_DATETIME")
    private Timestamp arrivalPlanDatetime;

    @Column(name = "CORNER_ARRIVAL_DATETIME")
    private Timestamp cornerArrivalDatetime;

    @Column(name = "HANDLE_DATETIME")
    private Timestamp handleDatetime;

    @Column(name = "CANCLE_REQ_DATETIME")
    private Timestamp cancleReqDatetime;

    @Column(name = "ELAPSED_TIME")
    private Timestamp elapsedTime;

    @Column(name = "CALLEE_REQ_SEQ_NO")
    private String calleeReqSeqNo;

    @Column(name = "ERROR_TYPE")
    private String errorType;

    @Column(name = "ERROR_PROCESS_STATUS")
    private int errorProcessStatus;

    @Column(name = "CASH_10K_EMPTY_STATUS")
    private String cash10kEmptyStatus;

    @Column(name = "CASH_50K_EMPTY_STATUS")
    private String cash50kEmptyStatus;

    @Column(name = "TX_ID")
    private String txId;

    // 조치사항관련 Properties
    @Transient
    private Timestamp startDate;

    @Transient
    private Timestamp endDate;

    @Transient
    private String noticeContent;

    @Transient
    private String customerInfo;

    @Transient
    private String handleContent;

    @Transient
    private Timestamp lastModifyDatetime;

    @Transient
    private String lastModifyEmpName;

    @Transient
    private String stextGubun;

    @Transient
    private String totalClassifyCode;

    @Transient
    private String customerWaitEnable;

    @Override
    public ErrorStatusId getId() {
        return ErrorStatusId.of(branchCode, cornerCode, terminalNo, errorDatetime);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ErrorStatusId implements Serializable {

        @NonNull
        private String branchCode;

        @NonNull
        private String cornerCode;

        @NonNull
        private String terminalNo;

        @NonNull
        private Timestamp errorDatetime;
    }
}