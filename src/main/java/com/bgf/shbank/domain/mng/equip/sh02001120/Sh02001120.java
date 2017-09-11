package com.bgf.shbank.domain.mng.equip.sh02001120;

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


@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ATMS_02001120")
@Comment("")
@Alias("sh02001120")
public class Sh02001120 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "WORK_SEQ_NO", length = 8, nullable = false)
	private String workSeqNo;

	@Column(name = "CHANGE_CHASU", length = 2)
	private String changeChasu;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "CLOSING_BRANCH_NO", length = 4)
	private String closingBranchNo;

	@Column(name = "CLOSING_CORNER_CODE", length = 2)
	private String closingCornerCode;

	@Column(name = "ADDR", length = 60)
	private String addr;

	@Column(name = "UNUSL", length = 100)
	private String unusl;


    @Override
    public String getId() {
        return workSeqNo;
    }
}