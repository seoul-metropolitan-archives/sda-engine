package com.bgf.shbank.domain.mng.equip.sh05001120;

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
@Table(name = "ATMS_05001120")
@Comment(value = "")
@IdClass(Sh05001120.Sh05001120Id.class)
@Alias("sh05001120")
public class Sh05001120 extends SimpleJpaModel<Sh05001120.Sh05001120Id> {

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

	@Column(name = "PHOTO_URL", length = 100)
	private String photoUrl;

	@Column(name = "OUTSIDE_BILLBOARD_PHOTO", length = 100)
	private String outsideBillboardPhoto;

	@Column(name = "GATE_PHOTO", length = 100)
	private String gatePhoto;

	@Column(name = "FLOAR_PHOTO", length = 100)
	private String floarPhoto;

	@Column(name = "BOOTH_LEFT_PHOTO", length = 100)
	private String boothLeftPhoto;

	@Column(name = "BOOTH_RIGHT_PHOTO", length = 100)
	private String boothRightPhoto;

	@Column(name = "CEILING_PHOTO", length = 100)
	private String ceilingPhoto;

	@Column(name = "TERMINAL_TOP_PHOTO", length = 100)
	private String terminalTopPhoto;

	@Column(name = "TERMINAL_BOTTOM_PHOTO", length = 100)
	private String terminalBottomPhoto;


@Override
public Sh05001120Id getId() {
return Sh05001120Id.of(branchCode, cornerCode, overhaulDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh05001120Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private Timestamp overhaulDate;

}
}