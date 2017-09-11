package com.bgf.shbank.domain.mng.sla.sh_sla_50;

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
@Table(name = "ATMS_SLA_50")
@Comment(value = "")
@IdClass(ShSla50.ShSla50Id.class)
@Alias("shSla50")
public class ShSla50 extends SimpleJpaModel<ShSla50.ShSla50Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "CORP_CODE", length = 2, nullable = false)
    private String corpCode;

    @Id
    @Column(name = "TX_DATETIME", nullable = false)
    private Timestamp txDatetime;

    @Column(name = "OCM_AMOUNT", length = 12)
    private String ocmAmount;

    @Column(name = "OCM_AMOUNT2", length = 12)
    private String ocmAmount2;

    @Column(name = "ACCOUNT_BALANCE", length = 12)
    private String accountBalance;


    @Override
    public ShSla50Id getId() {
        return ShSla50Id.of(txDate, corpCode, txDatetime);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSla50Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private String corpCode;

        @NonNull
        private Timestamp txDatetime;

    }
}