package com.bgf.shbank.domain.mng.equip.sh02001140;

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
@Table(name = "ATMS_02001140")
@Comment("")
@Alias("sh02001140")
public class Sh02001140 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "WORK_SEQ_NO", length = 8, nullable = false)
	private String workSeqNo;

	@Column(name = "TERMINAL_SEQ_NO", length = 2)
	private String terminalSeqNo;

	@Column(name = "CHANGE_CHASU", length = 2)
	private String changeChasu;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "NOUSE_JISA_CODE", length = 2, nullable = false)
	private String nouseJisaCode;

	@Column(name = "NOUSE_BRANCH_CODE", length = 4, nullable = false)
	private String nouseBranchCode;

	@Column(name = "NOUSE_CORNER_CODE", length = 2, nullable = false)
	private String nouseCornerCode;

	@Column(name = "NOUSE_TERMINAL_NO", length = 4, nullable = false)
	private String nouseTerminalNo;

	@Column(name = "TERMINAL_CORP_CODE", length = 1)
	private String terminalCorpCode;

	@Column(name = "MODEL_CODE", length = 3)
	private String modelCode;

	@Column(name = "HIRE_TERMINAL_ENABLE", length = 1)
	private String hireTerminalEnable;

	@Column(name = "NOUSE_GUBUN", length = 1)
	private String nouseGubun;

	@Column(name = "WORK_PLAN_DATETIME")
	private Timestamp workPlanDatetime;


    @Override
    public String getId() {
        return workSeqNo;
    }
}