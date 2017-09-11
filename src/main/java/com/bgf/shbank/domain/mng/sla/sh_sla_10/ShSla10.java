package com.bgf.shbank.domain.mng.sla.sh_sla_10;

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
@Table(name = "ATMS_SLA_10")
@Comment(value = "")
@IdClass(ShSla10.ShSla10Id.class)
@Alias("shSla10")
public class ShSla10 extends SimpleJpaModel<ShSla10.ShSla10Id> {

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

    @Column(name = "CALLEE_GUBUN", length = 1)
    private String calleeGubun;

    @Column(name = "JISA_CODE", length = 2)
    private String jisaCode;

    @Column(name = "BRANCH_CODE", length = 4)
    private String branchCode;

    @Column(name = "BRANCH_NAME", length = 100)
    private String branchName;

    @Column(name = "CORNER_CODE", length = 2)
    private String cornerCode;

    @Column(name = "CORNER_NAME", length = 40)
    private String cornerName;

    @Column(name = "TERMINAL_NO", length = 4)
    private String terminalNo;

    @Column(name = "SECURITY_CORP", length = 2)
    private String securityCorp;

    @Column(name = "CALLEE_REQ_REASON", length = 400)
    private String calleeReqReason;

    @Column(name = "CALLEE_REQ_DATETIME")
    private Timestamp calleeReqDatetime;

    @Column(name = "ARRIVAL_DATETIME")
    private Timestamp arrivalDatetime;

    @Column(name = "ELAPSED_TIME", length = 8)
    private String elapsedTime;

    @Column(name = "REPORT", length = 800)
    private String report;

    @Column(name = "ACCEPT", length = 1)
    private String accept;

    @Column(name = "REFUSE_REASON", length = 400)
    private String refuseReason;


    @Override
    public ShSla10Id getId() {
        return ShSla10Id.of(txDate, txId, errorDatetime, calleeChasu);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSla10Id implements Serializable {

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