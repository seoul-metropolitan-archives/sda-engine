package com.bgf.shbank.domain.mng.sla.sh_sla_e0;

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
@Table(name = "ATMS_SLA_E0")
@Comment(value = "")
@IdClass(ShSlaE0.ShSlaE0Id.class)
@Alias("shSlaE0")
public class ShSlaE0 extends SimpleJpaModel<ShSlaE0.ShSlaE0Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "STND_DATE", nullable = false)
    private Timestamp stndDate;

    @Id
    @Column(name = "TX_ID", length = 8, nullable = false)
    private String txId;

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

    @Column(name = "TERMINAL_NO", length = 4)
    private String terminalNo;

    @Column(name = "OPER_START_TIME", length = 4)
    private String operStartTime;

    @Column(name = "OPER_END_TIME", length = 4)
    private String operEndTime;

    @Column(name = "MODEL_NAME", length = 50)
    private String modelName;

    @Column(name = "TOTAL_OPER_TIME", length = 20)
    private String totalOperTime;

    @Column(name = "REAL_OPER_TIME", length = 20)
    private String realOperTime;

    @Column(name = "NONE_OPER_TIME", length = 20)
    private String noneOperTime;


    @Override
    public ShSlaE0Id getId() {
        return ShSlaE0Id.of(txDate, stndDate, txId);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSlaE0Id implements Serializable {
        @NonNull
        private Timestamp txDate;

        @NonNull
        private Timestamp stndDate;

        @NonNull
        private String txId;

    }
}