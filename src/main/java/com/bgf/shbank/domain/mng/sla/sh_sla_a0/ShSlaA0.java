package com.bgf.shbank.domain.mng.sla.sh_sla_a0;

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
@Table(name = "ATMS_SLA_A0")
@Comment(value = "")
@IdClass(ShSlaA0.ShSlaA0Id.class)
@Alias("shSlaA0")
public class ShSlaA0 extends SimpleJpaModel<ShSlaA0.ShSlaA0Id> {

    @Id
    @Column(name = "TX_DATE", nullable = false)
    private Timestamp txDate;

    @Id
    @Column(name = "REG_DATETIME", nullable = false)
    private Timestamp regDatetime;

    @Id
    @Column(name = "BRANCH_CODE", length = 4, nullable = false)
    private String branchCode;

    @Id
    @Column(name = "CORNER_CODE", length = 2, nullable = false)
    private String cornerCode;

    @Column(name = "JISA_CODE", length = 2)
    private String jisaCode;

    @Column(name = "OVERHAUL_DATE")
    private Timestamp overhaulDate;

    @Column(name = "BRANCH_NAME", length = 100)
    private String branchName;

    @Column(name = "CORNER_NAME", length = 50)
    private String cornerName;

    @Column(name = "OPER_DATE")
    private Timestamp operDate;

    @Column(name = "BRANCH_GUBUN", length = 10)
    private String branchGubun;

    @Column(name = "CORP_CODE", length = 2)
    private String corpCode;

    @Column(name = "TAKE_PHOTO_ENABLE", length = 10)
    private String takePhotoEnable;

    @Column(name = "SUCCESS_COUNT", length = 4)
    private String successCount;

    @Column(name = "OVERHAUL_COUNT", length = 4)
    private String overhaulCount;

    @Column(name = "OVERHAUL_RESULT", length = 4)
    private String overhaulResult;

    @Column(name = "OVERHAUL_UNUSL", length = 1024)
    private String overhaulUnusl;

    @Column(name = "SUBMIT_TARGET_COUNT", length = 4)
    private String submitTargetCount;

    @Column(name = "SUBMIT_COUNT", length = 4)
    private String submitCount;

    @Column(name = "PRODUCT_CONDT", length = 10)
    private String productCondt;

    @Column(name = "TERMINAL_CONDT", length = 10)
    private String terminalCondt;

    @Column(name = "GROUND_CONDT", length = 10)
    private String groundCondt;

    @Column(name = "GARBAGE_CONDT", length = 10)
    private String garbageCondt;

    @Column(name = "CASH_ENVELOPE_CONDT", length = 10)
    private String cashEnvelopeCondt;

    @Column(name = "POSTER_CONDT", length = 10)
    private String posterCondt;


    @Override
    public ShSlaA0Id getId() {
        return ShSlaA0Id.of(txDate, regDatetime, branchCode, cornerCode);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ShSlaA0Id implements Serializable {

        @NonNull
        private Timestamp txDate;

        @NonNull
        private Timestamp regDatetime;

        @NonNull
        private String branchCode;

        @NonNull
        private String cornerCode;

    }
}