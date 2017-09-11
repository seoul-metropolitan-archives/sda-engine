package com.bgf.shbank.domain.mng.equip.sh02001170;

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
@Table(name = "ATMS_02001170")
@Comment("")
@Alias("sh02001170")
public class Sh02001170 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "WORK_SEQ_NO", length = 8, nullable = false)
	private String workSeqNo;

	@Column(name = "FAC_SEQ_NO", length = 2)
	private String facSeqNo;

	@Column(name = "CHANGE_CHASU", length = 2)
	private String changeChasu;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "FAC_GUBUN_CODE", length = 2)
	private String facGubunCode;

	@Column(name = "FAC_CODE", length = 4, nullable = false)
	private String facCode;

	@Column(name = "HIRE_FAC_ENABLE", length = 1)
	private String hireFacEnable;

	@Column(name = "NOUSE_GUBUN", length = 1)
	private String nouseGubun;

	@Column(name = "ASSET_SEQ_NO", length = 12)
	private String assetSeqNo;


    @Override
    public String getId() {
        return workSeqNo;
    }
}