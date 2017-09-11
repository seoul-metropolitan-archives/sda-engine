package com.bgf.shbank.domain.mng.equip.overhaul_plan;

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
@Table(name = "ATMS_OVERHAUL_PLAN")
@Comment(value = "")
@IdClass(OverhaulPlan.OverhaulPlanId.class)
@Alias("overhaulPlan")
public class OverhaulPlan extends SimpleJpaModel<OverhaulPlan.OverhaulPlanId> {

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

	@Column(name = "OVERHAUL_GUBUN", length = 1)
	private String overhaulGubun;


@Override
public OverhaulPlanId getId() {
return OverhaulPlanId.of(branchCode, cornerCode, overhaulDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class OverhaulPlanId implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private Timestamp overhaulDate;

}
}