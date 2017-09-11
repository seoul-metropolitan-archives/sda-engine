package com.bgf.shbank.domain.mng.equip.sh02001270;

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
@Table(name = "ATMS_02001270")
@Comment("")
@Alias("sh02001270")
public class Sh02001270 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "WORK_SEQ_NO", length = 8, nullable = false)
	private String workSeqNo;

	@Column(name = "CHASU", length = 2)
	private String chasu;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "CLOSING_DATE")
	private Timestamp closingDate;

	@Column(name = "RESTART_GUBUN", length = 1)
	private String restartGubun;

	@Column(name = "RESTART_DATE")
	private Timestamp restartDate;

	@Column(name = "CHANGE_ITEM_GUBUN", length = 1)
	private String changeItemGubun;

	@Column(name = "OPER_START_TIME")
	private Timestamp operStartTime;

	@Column(name = "OPER_END_TIME")
	private Timestamp operEndTime;

	@Column(name = "CHECK_OPER", length = 1)
	private String checkOper;

	@Column(name = "APPLY_DATE", length = 8)
	private String applyDate;


    @Override
    public String getId() {
        return workSeqNo;
    }
}