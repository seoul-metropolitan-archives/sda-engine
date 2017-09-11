package com.bgf.shbank.domain.mng.equip.sh05001130;

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
@Table(name = "ATMS_05001130")
@Comment(value = "")
@IdClass(Sh05001130.Sh05001130Id.class)
@Alias("sh05001130")
public class Sh05001130 extends SimpleJpaModel<Sh05001130.Sh05001130Id> {

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

	@Column(name = "TERMINAL_FRONT_GLASS", length = 1)
	private String terminalFrontGlass;

	@Column(name = "TERMINAL_FRONT", length = 1)
	private String terminalFront;

	@Column(name = "TERMINAL_CRACKS", length = 1)
	private String terminalCracks;

	@Column(name = "MORNITOR", length = 1)
	private String mornitor;

	@Column(name = "BNKB_PART_ENTRANCE_CRACKS", length = 1)
	private String bnkbPartEntranceCracks;

	@Column(name = "CARD_PART_ENTRANCE_CRACKS", length = 1)
	private String cardPartEntranceCracks;

	@Column(name = "INTERCOM", length = 1)
	private String intercom;

	@Column(name = "BOARD_CRADLE", length = 1)
	private String boardCradle;

	@Column(name = "BRANCH_FLOAR", length = 1)
	private String branchFloar;

	@Column(name = "INSIDE_WALL", length = 1)
	private String insideWall;

	@Column(name = "OUTSIDE_WALL", length = 1)
	private String outsideWall;

	@Column(name = "BRANCH_GLASS", length = 1)
	private String branchGlass;

	@Column(name = "CEILING", length = 1)
	private String ceiling;

	@Column(name = "FLUORESCENT_LIGHT_GLASS", length = 1)
	private String fluorescentLightGlass;

	@Column(name = "STICKING_BILLBOARD", length = 1)
	private String stickingBillboard;

	@Column(name = "GARBAGECAN", length = 1)
	private String garbagecan;

	@Column(name = "COOLER_HEATER", length = 1)
	private String coolerHeater;

	@Column(name = "BRANCH_PERIPHERY", length = 1)
	private String branchPeriphery;

	@Column(name = "UNUSL", length = 80)
	private String unusl;

	@Column(name = "OVERHAUL_EMP", length = 20)
	private String overhaulEmp;

	@Column(name = "OFFICE_NAME", length = 40)
	private String officeName;

	@Column(name = "TAKE_PHOTO_ENABLE", length = 1)
	private String takePhotoEnable;

	@Column(name = "TRANSMIT_ROUND_GUBUN", length = 1)
	private String transmitRoundGubun;


@Override
public Sh05001130Id getId() {
return Sh05001130Id.of(branchCode, cornerCode, overhaulDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh05001130Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private Timestamp overhaulDate;

}
}