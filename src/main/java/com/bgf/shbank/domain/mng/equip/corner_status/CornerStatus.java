package com.bgf.shbank.domain.mng.equip.corner_status;

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
@Table(name = "ATMS_CORNER_STATUS")
@Comment(value = "")
@IdClass(CornerStatus.CornerStatusId.class)
@Alias("cornerStatus")
public class CornerStatus extends SimpleJpaModel<CornerStatus.CornerStatusId> {

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

	@Column(name = "PLACE_GUBUN", length = 1)
	private String placeGubun;

	@Column(name = "BRANCH_GUBUN", length = 1)
	private String branchGubun;

	@Column(name = "SPECIAL_STYLE_GUBUN", length = 1)
	private String specialStyleGubun;

	@Column(name = "OPER_TIME_GUBUN", length = 1)
	private String operTimeGubun;

	@Column(name = "OPER_START_TIME")
	private Timestamp operStartTime;

	@Column(name = "OPER_END_TIME")
	private Timestamp operEndTime;

	@Column(name = "CHECK_OPER_ENABLE", length = 1)
	private String checkOperEnable;

	@Column(name = "SEVICE_FEE", length = 10)
	private String seviceFee;

	@Column(name = "SECURITY_CORP_CODE", length = 2)
	private String securityCorpCode;

	@Column(name = "FAC_HIRE_ENABLE", length = 1)
	private String facHireEnable;

	@Column(name = "ADDR", length = 60)
	private String addr;

	@Column(name = "UNUSL", length = 100)
	private String unusl;

	@Column(name = "OPER_DAY", length = 7)
	private String operDay;


@Override
public CornerStatusId getId() {
return CornerStatusId.of(jisaCode, branchCode, cornerCode);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class CornerStatusId implements Serializable {

		@NonNull
		private String jisaCode;

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

}
}