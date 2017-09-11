package com.bgf.shbank.domain.mng.equip.facility_status;

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
@Table(name = "ATMS_FACILITY_STATUS")
@Comment(value = "")
@IdClass(FacilityStatus.FacilityStatusId.class)
@Alias("facilityStatus")
public class FacilityStatus extends SimpleJpaModel<FacilityStatus.FacilityStatusId> {

	@Id
	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "BRANCH_NAME", length = 40)
	private String branchName;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "FAC_GUBUN_CODE", length = 2)
	private String facGubunCode;

	@Column(name = "FAC_CODE", length = 4, nullable = false)
	private String facCode;

	@Column(name = "OPER_ENABLE", length = 1)
	private String operEnable;

	@Column(name = "HIRE_FAC_ENABLE", length = 1)
	private String hireFacEnable;

	@Column(name = "INSTALL_ARTICLE_GUBUN", length = 1)
	private String installArticleGubun;

	@Column(name = "ASSET_SEQ_NO", length = 12)
	private String assetSeqNo;

	@Column(name = "FAC_IP_ADDR", length = 15)
	private String facIpAddr;

	@Column(name = "FAC_GW_ADDR", length = 15)
	private String facGwAddr;

	@Column(name = "FAC_SMASK_ADDR", length = 15)
	private String facSmaskAddr;

	@Column(name = "HIRE_FEE", length = 10)
	private String hireFee;

	@Column(name = "DETAIL_FAC_INFO", length = 200)
	private String detailFacInfo;

	@Column(name = "ADOPT_DATE")
	private Timestamp adoptDate;

	@Column(name = "INSTALL_DATE")
	private Timestamp installDate;

	@Column(name = "REMARKS", length = 200)
	private String remarks;

	@Column(name = "UNIT_PRICE", length = 15)
	private String unitPrice;

@Override
public FacilityStatusId getId() {
return FacilityStatusId.of(jisaCode, branchCode, cornerCode);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class FacilityStatusId implements Serializable {

		@NonNull
		private String jisaCode;

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

}
}