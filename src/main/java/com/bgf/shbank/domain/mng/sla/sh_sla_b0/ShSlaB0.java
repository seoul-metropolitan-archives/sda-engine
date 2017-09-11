package com.bgf.shbank.domain.mng.sla.sh_sla_b0;

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
@Table(name = "ATMS_SLA_B0")
@Comment(value = "")
@IdClass(ShSlaB0.ShSlaB0Id.class)
@Alias("shSlaB0")
public class ShSlaB0 extends SimpleJpaModel<ShSlaB0.ShSlaB0Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "TX_ID", length = 8, nullable = false)
    private String txId;

    @Column(name = "TOTAL_CORP", length = 2)
    private String totalCorp;

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

    @Column(name = "MNG_GUBUN", length = 1)
    private String mngGubun;

    @Column(name = "OPER_TIME_GUBUN", length = 1)
    private String operTimeGubun;

    @Column(name = "TERMINAL_CORP_NAME", length = 20)
    private String terminalCorpName;

    @Column(name = "MODEL_NAME", length = 50)
    private String modelName;

    @Column(name = "ATM_GUBUN", length = 10)
    private String atmGubun;

    @Id
    @Column(name = "OVERHAUL_DATETIME", nullable = false)
    private Timestamp overhaulDatetime;


    @Override
    public ShSlaB0Id getId() {
        return ShSlaB0Id.of(txDate, txId, overhaulDatetime);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSlaB0Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private String txId;

        @NonNull
        private Timestamp overhaulDatetime;

    }
}