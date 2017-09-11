package com.bgf.shbank.domain.mng.equip.sh02001200;

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
@Table(name = "ATMS_02001200")
@Comment("")
@Alias("sh02001200")
public class Sh02001200 extends SimpleJpaModel<Timestamp> {

	@Id
	@Column(name = "STEXT_NOTICE_DATETIME", nullable = false)
	private Timestamp stextNoticeDatetime;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "PLACE_GUBUN", length = 1)
	private String placeGubun;

	@Column(name = "BRANCH_GUBUN", length = 1)
	private String branchGubun;

	@Column(name = "OPER_START_GUBUN", length = 1)
	private String operStartGubun;

	@Column(name = "OPER_START_TIME")
	private Timestamp operStartTime;

	@Column(name = "OPER_END_TIME")
	private Timestamp operEndTime;

	@Column(name = "SECURITY_CORP_CODE", length = 2)
	private String securityCorpCode;

	@Column(name = "CHECK_OPER_ENABLE", length = 1)
	private String checkOperEnable;

	@Column(name = "OPER_DAY", length = 7)
	private String operDay;

	@Column(name = "ADDR", length = 60)
	private String addr;

	@Column(name = "INSTALL_PLACE", length = 60)
	private String installPlace;

	@Column(name = "DETAIL_FAC_INFO", length = 150)
	private String detailFacInfo;


    @Override
    public Timestamp getId() {
        return stextNoticeDatetime;
    }
}