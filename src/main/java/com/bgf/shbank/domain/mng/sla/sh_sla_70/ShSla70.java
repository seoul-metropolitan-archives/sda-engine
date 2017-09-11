package com.bgf.shbank.domain.mng.sla.sh_sla_70;

import io.onsemiro.core.annotations.Comment;
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
@Table(name = "ATMS_SLA_70")
@Comment(value = "")
@IdClass(ShSla70.ShSla70Id.class)
@Alias("shSla70")
public class ShSla70 extends SimpleJpaModel<ShSla70.ShSla70Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "TX_ID", length = 8, nullable = false)
    private String txId;

    @Id
    @Column(name = "ERROR_DATETIME", nullable = false)
    private Timestamp errorDatetime;

    @Id
    @Column(name = "CALLEE_CHASU", length = 2, nullable = false)
    private String calleeChasu;

    @Column(name = "CUSTOMER_WAIT", length = 1)
    private String customerWait;

    @Column(name = "EVAL_TYPE", length = 10)
    private String evalType;

    @Column(name = "CORP_CODE", length = 2)
    private String corpCode;

    @Column(name = "CALLEE_TYPE", length = 10)
    private String calleeType;

    @Column(name = "JISA_CODE", length = 2)
    private String jisaCode;

    @Column(name = "BRANCH_CODE", length = 4)
    private String branchCode;

    @Column(name = "BRANCH_NAME", length = 100)
    private String branchName;

    @Column(name = "CORNER_CODE", length = 2)
    private String cornerCode;

    @Column(name = "CORNER_NAME", length = 50)
    private String cornerName;

    @Column(name = "LOCATION", length = 20)
    private String location;

    @Column(name = "AREA", length = 10)
    private String area;

    @Column(name = "TERMINAL_NO", length = 4)
    private String terminalNo;

    @Column(name = "CALLEE_GUBUN", length = 10)
    private String calleeGubun;

    @Column(name = "CALLEE_REQ_DATETIME")
    private Timestamp calleeReqDatetime;

    @Column(name = "ARRIVAL_PLAN_DATETIME")
    private Timestamp arrivalPlanDatetime;

    @Column(name = "ARRIVAL_DATETIME")
    private Timestamp arrivalDatetime;

    @Column(name = "ARRIVAL_TYPE", length = 20)
    private String arrivalType;

    @Column(name = "CALLEE_REQ_ELAPSED_TIME", length = 8)
    private String calleeReqElapsedTime;

    @Column(name = "CALLEE_REQ_ELAPSED_SECONDS", length = 8)
    private String calleeReqElapsedSeconds;

    @Column(name = "ARRIVAL_ELAPSED_TIME", length = 8)
    private String arrivalElapsedTime;

    @Column(name = "ARRIVAL_ELAPSED_SECONDS", length = 8)
    private String arrivalElapsedSeconds;

    @Column(name = "REPORT_DATETIME")
    private Timestamp reportDatetime;

    @Column(name = "ERROR_CONTENT", length = 40)
    private String errorContent;

    @Column(name = "MODEL_NAME", length = 40)
    private String modelName;

    @Column(name = "ACCEPT", length = 1)
    private String accept;

    @Column(name = "REFUSE_REASON", length = 400)
    private String refuseReason;


    @Override
    public ShSla70Id getId() {
        return ShSla70Id.of(txDate, txId, errorDatetime, calleeChasu);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSla70Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private String txId;

        @NonNull
        private Timestamp errorDatetime;

        @NonNull
        private String calleeChasu;

    }
}