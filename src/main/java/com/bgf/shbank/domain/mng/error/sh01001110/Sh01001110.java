package com.bgf.shbank.domain.mng.error.sh01001110;

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
@Table(name = "ATMS_01001110")
@Comment(value = "")
@IdClass(Sh01001110.Sh01001110Id.class)
@Alias("sh01001110")
public class Sh01001110 extends SimpleJpaModel<Sh01001110.Sh01001110Id> {

    @Id
    @Column(name = "ERROR_DATETIME", nullable = false)
    private Timestamp errorDatetime;

    @Column(name = "JISA_CODE", length = 2, nullable = false)
    private String jisaCode;

    @Id
    @Column(name = "BRANCH_CODE", length = 4, nullable = false)
    private String branchCode;

    @Column(name = "BRANCH_NAME", length = 40)
    private String branchName;

    @Column(name = "BRANCH_TEL", length = 15)
    private String branchTel;

    @Id
    @Column(name = "CORNER_CODE", length = 2, nullable = false)
    private String cornerCode;

    @Column(name = "CORNER_NAME", length = 40)
    private String cornerName;

    @Id
    @Column(name = "TERMINAL_NO", length = 4, nullable = false)
    private String terminalNo;

    @Column(name = "PLACE_GUBUN", length = 1)
    private String placeGubun;

    @Column(name = "OPER_TIME_GUBUN", length = 1)
    private String operTimeGubun;

    @Column(name = "RUNTIME", length = 11)
    private String runtime;

    @Column(name = "PROD_SERIAL_NO", length = 15)
    private String prodSerialNo;

    @Column(name = "TERMINAL_CORP_CODE", length = 1)
    private String terminalCorpCode;

    @Column(name = "MODEL_CODE", length = 3)
    private String modelCode;

    @Column(name = "PROGRESS_STATUS", length = 1)
    private String progressStatus;

    @Column(name = "STEXT_GUBUN", length = 1)
    private String stextGubun;

    @Column(name = "TERMINAL_ERROR1", length = 5)
    private String terminalError1;

    @Column(name = "TERMINAL_ERROR2", length = 10)
    private String terminalError2;

    @Column(name = "TOTAL_CLASSIFY_CODE", length = 2)
    private String totalClassifyCode;

    @Column(name = "ERROR_TYPE", length = 1)
    private String errorType;

    @Column(name = "CASH_PART_STATUS", length = 1)
    private String cashPartStatus;

    @Column(name = "CHECK_PART_STATUS", length = 1)
    private String checkPartStatus;

    @Column(name = "PORTFOLIO_PART_STATUS", length = 1)
    private String portfolioPartStatus;

    @Column(name = "JRNL_PART_STATUS", length = 1)
    private String jrnlPartStatus;

    @Column(name = "RTRVL_BOX_STATUS", length = 1)
    private String rtrvlBoxStatus;

    @Column(name = "CARD_PART_STATUS", length = 1)
    private String cardPartStatus;

    @Column(name = "BNKB_PART_STATUS", length = 1)
    private String bnkbPartStatus;

    @Column(name = "GIRO_PART_STATUS", length = 1)
    private String giroPartStatus;

    @Column(name = "SUSPEND_STATUS", length = 1)
    private String suspendStatus;

    @Column(name = "HW_ERROR_STATUS", length = 1)
    private String hwErrorStatus;

    @Column(name = "MAINTENANCE_STATUS", length = 1)
    private String maintenanceStatus;

    @Column(name = "VERSION", length = 8)
    private String version;

    @Column(name = "ENABLE_DES_BOARD", length = 1)
    private String enableDesBoard;

    @Column(name = "ENABLE_IC_CARD", length = 1)
    private String enableIcCard;

    @Column(name = "ENABLE_EMV", length = 1)
    private String enableEmv;

    @Column(name = "ENABLE_IR", length = 1)
    private String enableIr;

    @Column(name = "ENABLE_RF", length = 1)
    private String enableRf;

    @Column(name = "ENABLE_FINGERPRINT", length = 1)
    private String enableFingerprint;

    @Column(name = "ENCRYPTION_STATUS", length = 1)
    private String encryptionStatus;

    @Column(name = "TERMINAL_INFO", length = 100)
    private String terminalInfo;

    @Column(name = "ATMS_TERMINAL_NO", length = 10)
    private String atmsTerminalNo;

    @Column(name = "CASH_PART_STATUS_50K_WON", length = 1)
    private String cashPartStatus50kWon;

    @Column(name = "ATMC_EXCCLC_TERMINAL_ERROR", length = 1)
    private String atmcExcclcTerminalError;

    @Column(name = "ATMC_EXCCLC_EXEC_RESULT", length = 1)
    private String atmcExcclcExecResult;

    @Column(name = "TERMINAL_VER_INFO", length = 10)
    private String terminalVerInfo;

    @Column(name = "CARD_ISSUED_TERMINAL_STATUS", length = 1)
    private String cardIssuedTerminalStatus;

    @Column(name = "ID_SCANNER_STATUS", length = 1)
    private String idScannerStatus;

    @Column(name = "BIO_SCANNER_STATUS", length = 1)
    private String bioScannerStatus;

    @Column(name = "SCRTY_CARD_HIGHEND_ATM_ONLY", length = 1)
    private String scrtyCardHighendAtmOnly;

    @Column(name = "CARD_STYLE_OTP", length = 1)
    private String cardStyleOtp;

    @Column(name = "S20_GENERAL", length = 1)
    private String s20General;

    @Column(name = "S20_FRPY", length = 1)
    private String s20Frpy;

    @Column(name = "SLINE_GENERAL", length = 1)
    private String slineGeneral;

    @Column(name = "SLINE_FRPY", length = 1)
    private String slineFrpy;

    @Column(name = "FOURTUNE_GENERAL", length = 1)
    private String fourtuneGeneral;

    @Column(name = "FOURTUNE_FRPY", length = 1)
    private String fourtuneFrpy;

    @Column(name = "RCPPAY_BNKB", length = 1)
    private String rcppayBnkb;

    @Transient
    private Timestamp errorDatetime1;

    @Transient
    private String securityCorp;

    @Transient
    private String securityCorpContractNo;

    @Transient
    private String boothCorp;

    @Transient
    private String boothType;

    @Transient
    private String intercomNo;

    @Transient
    private String terminalLocation;

    @Transient
    private String commOffice;

    @Transient
    private String internetClassify;

    @Transient
    private String mngChannel;

    @Transient
    private Timestamp startDate;

    @Transient
    private Timestamp endDate;

    @Transient
    private String noticeContent;

    @Transient
    private String customerInfo;

    @Transient
    private String handleContent;

    @Transient
    private String customerWaitEnable;

    @Transient
    private String calleeReqSeqNo;

    @Transient
    private String txId;

    @Override
    public Sh01001110Id getId() {
        return Sh01001110Id.of(errorDatetime, branchCode, cornerCode, terminalNo);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class Sh01001110Id implements Serializable {

        @NonNull
        private Timestamp errorDatetime;

        @NonNull
        private String branchCode;

        @NonNull
        private String cornerCode;

        @NonNull
        private String terminalNo;
    }

}