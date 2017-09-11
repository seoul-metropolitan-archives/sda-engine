package com.bgf.shbank.domain.mng.equip.sh05001110;

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
@Table(name = "ATMS_05001110")
@Comment(value = "")
@IdClass(Sh05001110.Sh05001110Id.class)
@Alias("sh05001110")
public class Sh05001110 extends SimpleJpaModel<Sh05001110.Sh05001110Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Id
	@Column(name = "OVERHAUL_DATE", nullable = false)
	private Timestamp overhaulDate;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "TERMINAL_INSIDE_CLEANING", length = 1)
	private String terminalInsideCleaning;

	@Column(name = "TERMINAL_CLEANING", length = 1)
	private String terminalCleaning;

	@Column(name = "INTERCOM_CLEANING", length = 1)
	private String intercomCleaning;

	@Column(name = "FLOAR_CLEANING", length = 1)
	private String floarCleaning;

	@Column(name = "GARBAGECAN_CLEANING", length = 1)
	private String garbagecanCleaning;

	@Column(name = "GLASS_CLEAN_CONFIRM", length = 1)
	private String glassCleanConfirm;

	@Column(name = "PORTFOLIO_PAPER", length = 1)
	private String portfolioPaper;

	@Column(name = "JRNL_PAPER", length = 1)
	private String jrnlPaper;

	@Column(name = "BNKB_RIBBON", length = 1)
	private String bnkbRibbon;

	@Column(name = "NOTICEGUIDE", length = 1)
	private String noticeguide;

	@Column(name = "ETC_NOTICE", length = 1)
	private String etcNotice;

	@Column(name = "INTERCOM", length = 1)
	private String intercom;

	@Column(name = "DVR", length = 1)
	private String dvr;

	@Column(name = "FLUORESCENT_LIGHT", length = 1)
	private String fluorescentLight;

	@Column(name = "COOLER", length = 1)
	private String cooler;

	@Column(name = "HEATER", length = 1)
	private String heater;

	@Column(name = "BOOTH_OVERHAUL", length = 1)
	private String boothOverhaul;

	@Column(name = "CHECK_RESIDUAL_QUANTITY", length = 1)
	private String checkResidualQuantity;

	@Column(name = "RTRVL_BOX", length = 1)
	private String rtrvlBox;

	@Column(name = "BILLBOARD_LIGHTING", length = 1)
	private String billboardLighting;

	@Column(name = "UNUSL", length = 80)
	private String unusl;

	@Column(name = "REPORT_EMP", length = 20)
	private String reportEmp;

	@Column(name = "OFFICE_NAME", length = 40)
	private String officeName;

	@Column(name = "TAKE_PHOTO_ENABLE", length = 1)
	private String takePhotoEnable;


@Override
public Sh05001110Id getId() {
return Sh05001110Id.of(branchCode, cornerCode, overhaulDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh05001110Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private Timestamp overhaulDate;

}
}