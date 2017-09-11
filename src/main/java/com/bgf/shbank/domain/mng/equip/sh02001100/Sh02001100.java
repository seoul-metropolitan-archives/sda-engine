package com.bgf.shbank.domain.mng.equip.sh02001100;

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
@Table(name = "ATMS_02001100")
@Comment("")
@Alias("sh02001100")
public class Sh02001100 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "WORK_SEQ_NO", length = 8, nullable = false)
	private String workSeqNo;

	@Column(name = "CHANGE_CHASU", length = 2)
	private String changeChasu;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "WORK_GUBUN", length = 2)
	private String workGubun;

	@Column(name = "NOTICE_DATETIME")
	private Timestamp noticeDatetime;

	@Column(name = "TASK_APPLY_DATE")
	private Timestamp taskApplyDate;

	@Column(name = "WORK_DATE")
	private Timestamp workDate;

	@Column(name = "INSTALL_TERMINAL_COUNT", length = 2)
	private String installTerminalCount;

	@Column(name = "INSTALL_FAC_COUNT", length = 2)
	private String installFacCount;

	@Column(name = "BRANCH_NAME", length = 40)
	private String branchName;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "BIZ_MANAGER_NAME", length = 20)
	private String bizManagerName;

	@Column(name = "MANAGER_TELNO", length = 20)
	private String managerTelno;

	@Column(name = "WORK_CONTENT", length = 200)
	private String workContent;


    @Override
    public String getId() {
        return workSeqNo;
    }

}