package com.bgf.shbank.domain.mng.etc.sh04001130;

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
@Table(name = "ATMS_04001130")
@IdClass(Sh04001130.Sh04001130Id.class)
@Alias("sh04001130")
public class Sh04001130 extends SimpleJpaModel<Sh04001130.Sh04001130Id> {

	@Id
	@Column(name = "SERVICE_FEE_CALC_YEAR_MONTH", nullable = false)
	private Timestamp serviceFeeCalcYearMonth;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "BRANCH_GUBUN_CODE", length = 1)
	private String branchGubunCode;

	@Column(name = "OPER_TIME_GUBUN_CODE", length = 1)
	private String operTimeGubunCode;

	@Column(name = "TERMINAL_COUNT", length = 3)
	private String terminalCount;

	@Column(name = "CHECK_OPER_ENABLE", length = 1)
	private String checkOperEnable;

	@Column(name = "BASIC_SERVICE_FEE", length = 15)
	private String basicServiceFee;

	@Column(name = "TERMINAL_ADD_SERVICE_FEE", length = 15)
	private String terminalAddServiceFee;

	@Column(name = "TIME_ADD_SERVICE_FEE", length = 15)
	private String timeAddServiceFee;

	@Column(name = "CHECK_ADD_SERVICE_FEE", length = 15)
	private String checkAddServiceFee;

	@Column(name = "OPER_DATE_COUNT", length = 2)
	private String operDateCount;

	@Column(name = "MONTH_SERVICE_FEE", length = 15)
	private String monthServiceFee;

	@Column(name = "PENALTY", length = 15)
	private String penalty;

	@Column(name = "TOTAL_GIVE_SERVICE_FEE", length = 15)
	private String totalGiveServiceFee;

	@Column(name = "NOTE", length = 100)
	private String note;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

	@Override
	public Sh04001130Id getId() {
		return Sh04001130Id.of(serviceFeeCalcYearMonth, branchCode, cornerCode);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh04001130Id implements Serializable {

		@NonNull
		private Timestamp serviceFeeCalcYearMonth;

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;
	}

}