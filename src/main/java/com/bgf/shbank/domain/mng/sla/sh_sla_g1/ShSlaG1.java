package com.bgf.shbank.domain.mng.sla.sh_sla_g1;

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
@Table(name = "ATMS_SLA_G1")
@Comment(value = "")
@IdClass(ShSlaG1.ShSlaG1Id.class)
@Alias("shSlaG1")
public class ShSlaG1 extends SimpleJpaModel<ShSlaG1.ShSlaG1Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "WITHDRAW_DATETIME", nullable = false)
    private Timestamp withdrawDatetime;

    @Column(name = "REAL_CLASSIFY", length = 20)
    private String realClassify;

    @Id
    @Column(name = "REAL_NO", length = 100, nullable = false)
    private String realNo;

    @Column(name = "ISSUE_ORG", length = 20)
    private String issueOrg;

    @Column(name = "WITHDRAW_ORG", length = 20)
    private String withdrawOrg;

    @Id
    @Column(name = "TX_ID", length = 8, nullable = false)
    private String txId;

    @Column(name = "JISA_CODE", length = 2)
    private String jisaCode;

    @Column(name = "PROGRESS_STATUS", length = 1)
    private String progressStatus;

    @Column(name = "WITHDRAW_EMP_NAME", length = 20)
    private String withdrawEmpName;

    @Column(name = "TRANSFER_EMP_NAME", length = 20)
    private String transferEmpName;

    @Column(name = "TAKEOVER_EMP_NAME", length = 20)
    private String takeoverEmpName;

    @Column(name = "RETURN_EMP_NAME", length = 20)
    private String returnEmpName;

    @Column(name = "RECEIVE_EMP_NAME", length = 20)
    private String receiveEmpName;

    @Column(name = "TRANSFER_DATETIME")
    private Timestamp transferDatetime;

    @Column(name = "RETURN_DATETIME")
    private Timestamp returnDatetime;

    @Column(name = "STORAGE_UNUSL", length = 1024)
    private String storageUnusl;


    @Override
    public ShSlaG1Id getId() {
        return ShSlaG1Id.of(txDate, withdrawDatetime, realNo, txId);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSlaG1Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private Timestamp withdrawDatetime;

        @NonNull
        private String realNo;

        @NonNull
        private String txId;

    }
}