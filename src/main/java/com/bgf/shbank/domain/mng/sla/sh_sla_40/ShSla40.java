package com.bgf.shbank.domain.mng.sla.sh_sla_40;

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
@Table(name = "ATMS_SLA_40")
@Comment("")
@IdClass(ShSla40.ShSla40Id.class)
@Alias("shSla40")
public class ShSla40 extends SimpleJpaModel<ShSla40.ShSla40Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "SEQ_NO", length = 10, nullable = false)
    private String seqNo;

    @Column(name = "CORP_CODE", length = 2)
    private String corpCode;

    @Column(name = "GUBUN", length = 10)
    private String gubun;

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

    @Column(name = "TERMINAL_NO", length = 4)
    private String terminalNo;

    @Column(name = "CALLEE_REQ_DATETIME")
    private Timestamp calleeReqDatetime;

    @Column(name = "CHECKPOINT_SCORE1", length = 1)
    private String checkpointScore1;

    @Column(name = "CHECKPOINT_SCORE2", length = 1)
    private String checkpointScore2;

    @Column(name = "CHECKPOINT_SCORE3", length = 1)
    private String checkpointScore3;

    @Column(name = "CHECKPOINT_SCORE4", length = 1)
    private String checkpointScore4;

    @Column(name = "HAPPYCALL_AGENT", length = 20)
    private String happycallAgent;

    @Column(name = "HAPPYCALL_DATETIME")
    private Timestamp happycallDatetime;

    @Column(name = "ERROR_DATETIME")
    private Timestamp errorDatetime;

    @Column(name = "CALLEE_REQ_USER", length = 20)
    private String calleeReqUser;

    @Column(name = "CUSTOMER", length = 20)
    private String customer;

    @Column(name = "TEL_NO", length = 20)
    private String telNo;

    @Column(name = "CONTENT", length = 1024)
    private String content;


    @Override
    public ShSla40.ShSla40Id getId() {
        return ShSla40.ShSla40Id.of(txDate, seqNo);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSla40Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private String seqNo;

    }
}