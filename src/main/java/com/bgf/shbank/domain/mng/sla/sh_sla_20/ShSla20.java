package com.bgf.shbank.domain.mng.sla.sh_sla_20;

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
@Table(name = "ATMS_SLA_20")
@Comment(value = "")
@IdClass(ShSla20.ShSla20Id.class)
@Alias("shSla20")
public class ShSla20 extends SimpleJpaModel<ShSla20.ShSla20Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "TX_ID", length = 8, nullable = false)
    private String txId;

    @Id
    @Column(name = "ERROR_DATETIME", nullable = false)
    private Timestamp errorDatetime;

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

    @Column(name = "CORNER_NAME", length = 40)
    private String cornerName;

    @Column(name = "TERMINAL_NO", length = 4)
    private String terminalNo;

    @Column(name = "RECOVERY_DATETIME")
    private Timestamp recoveryDatetime;

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
    public ShSla20Id getId() {
        return ShSla20Id.of(txDate, txId, errorDatetime);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSla20Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private String txId;

        @NonNull
        private Timestamp errorDatetime;

    }
}