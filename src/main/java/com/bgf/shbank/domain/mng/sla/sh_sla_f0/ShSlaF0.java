package com.bgf.shbank.domain.mng.sla.sh_sla_f0;

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
@Table(name = "ATMS_SLA_F0")
@Comment(value = "")
@IdClass(ShSlaF0.ShSlaF0Id.class)
@Alias("shSlaF0")
public class ShSlaF0 extends SimpleJpaModel<ShSlaF0.ShSlaF0Id> {

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

    @Column(name = "CORP_CODE", length = 2)
    private String corpCode;

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

    @Column(name = "CALLEE_GUBUN", length = 20)
    private String calleeGubun;

    @Column(name = "CALLEE_RESULT", length = 20)
    private String calleeResult;

    @Column(name = "CALLEE_REQ_DATETIME")
    private Timestamp calleeReqDatetime;

    @Column(name = "ARRIVAL_DATETIME")
    private Timestamp arrivalDatetime;

    @Column(name = "HANDLE_DATETIME")
    private Timestamp handleDatetime;

    @Column(name = "RECOVER_DATETIME")
    private Timestamp recoverDatetime;

    @Column(name = "ELAPSED_TIME", length = 8)
    private String elapsedTime;

    @Column(name = "ELAPSED_SECONDS", length = 8)
    private String elapsedSeconds;

    @Column(name = "ERROR_CONTENT", length = 40)
    private String errorContent;

    @Column(name = "MODEL_NAME", length = 40)
    private String modelName;

    @Column(name = "ACCEPT", length = 1)
    private String accept;

    @Column(name = "REFUSE_REASON", length = 400)
    private String refuseReason;


    @Override
    public ShSlaF0Id getId() {
        return ShSlaF0Id.of(txDate, txId, errorDatetime, calleeChasu);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSlaF0Id implements Serializable {

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