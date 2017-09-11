package com.bgf.shbank.domain.mng.equip.sh02001220;

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
@Table(name = "ATMS_02001220")
@Comment("")
@Alias("sh02001220")
public class Sh02001220 extends SimpleJpaModel<String> {

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

	@Column(name = "NOUSE_BRANCH_CODE", length = 4)
	private String nouseBranchCode;

	@Column(name = "NOUSE_CORNER_CODE", length = 2)
	private String nouseCornerCode;

	@Column(name = "NOUSE_FAC_GUBUN_CODE", length = 2)
	private String nouseFacGubunCode;

	@Column(name = "NOUSE_FAC_CODE", length = 4)
	private String nouseFacCode;

	@Column(name = "NOUSE_ASSET_SEQ_NO", length = 12)
	private String nouseAssetSeqNo;

	@Column(name = "NOUSE_GUBUN", length = 1)
	private String nouseGubun;

	@Column(name = "NEW_BRANCH_CODE", length = 4)
	private String newBranchCode;

	@Column(name = "NEW_CORNER_CODE", length = 2)
	private String newCornerCode;

	@Column(name = "NEW_FAC_GUBUN_CODE", length = 2)
	private String newFacGubunCode;

	@Column(name = "NEW_FAC_CODE", length = 4)
	private String newFacCode;

	@Column(name = "NEW_HIRE_FAC_ENABLE", length = 1)
	private String newHireFacEnable;

	@Column(name = "NEW_INSTALL_ARTICLE_GUBUN", length = 1)
	private String newInstallArticleGubun;

	@Column(name = "NEW_ASSET_SEQ_NO", length = 12)
	private String newAssetSeqNo;

	@Column(name = "FAC_IP_ADDR", length = 15)
	private String facIpAddr;

	@Column(name = "FAC_GW_ADDR", length = 15)
	private String facGwAddr;

	@Column(name = "FAC_SMASK_ADDR", length = 15)
	private String facSmaskAddr;

	@Column(name = "NEW_HIRE_FEE", length = 10)
	private String newHireFee;


    @Override
    public String getId() {
        return workSeqNo;
    }
}