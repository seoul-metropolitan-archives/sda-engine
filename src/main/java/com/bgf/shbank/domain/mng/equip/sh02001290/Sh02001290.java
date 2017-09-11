package com.bgf.shbank.domain.mng.equip.sh02001290;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ATMS_02001290")
@Comment("")
@Alias("sh02001290")
public class Sh02001290 extends SimpleJpaModel<Timestamp> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "BILLING_MONTH")
	private Timestamp billingMonth;

	@Column(name = "COST_GUBUN", length = 2)
	private String costGubun;

	@Column(name = "DETAIL_ITEM_GUBUN", length = 2)
	private String detailItemGubun;

	@Column(name = "SEQ_NO", length = 20)
	private String seqNo;

	@Column(name = "WORK_DATE")
	private Timestamp workDate;

	@Column(name = "INSPECTION_MONTH")
	private Timestamp inspectionMonth;

	@Column(name = "BILLING_AMT", length = 8)
	private String billingAmt;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "DETAIL_CONTENT", length = 200)
	private String detailContent;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;


    @Override
    public Timestamp getId() {
        return txId;
    }
}