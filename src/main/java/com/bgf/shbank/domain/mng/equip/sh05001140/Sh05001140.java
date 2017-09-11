package com.bgf.shbank.domain.mng.equip.sh05001140;

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
@Table(name = "ATMS_05001140")
@Comment(value = "")
@IdClass(Sh05001140.Sh05001140Id.class)
@Alias("sh05001140")
public class Sh05001140 extends SimpleJpaModel<Sh05001140.Sh05001140Id> {

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

	@Column(name = "TERMINAL_FRONT_GLASS", length = 10)
	private String terminalFrontGlass;

	@Column(name = "TERMINAL_FRONT", length = 10)
	private String terminalFront;

	@Column(name = "TERMINAL_CRACKS", length = 10)
	private String terminalCracks;

	@Column(name = "MONITOR", length = 10)
	private String monitor;

	@Column(name = "BNKB_PART_ENTRANCE_CRACKS", length = 10)
	private String bnkbPartEntranceCracks;

	@Column(name = "CARD_PART_ENTRANCE_CRACKS", length = 10)
	private String cardPartEntranceCracks;

	@Column(name = "INTERCOM", length = 10)
	private String intercom;

	@Column(name = "BOARD_CRADLE", length = 10)
	private String boardCradle;

	@Column(name = "BRANCH_FLOOR", length = 10)
	private String branchFloor;

	@Column(name = "INSIDE_WALL", length = 10)
	private String insideWall;

	@Column(name = "OUTSIDE_WALL", length = 10)
	private String outsideWall;

	@Column(name = "BRANCH_GLASS", length = 10)
	private String branchGlass;

	@Column(name = "CEILING", length = 10)
	private String ceiling;

	@Column(name = "FLUORESCENT_LIGHT", length = 10)
	private String fluorescentLight;

	@Column(name = "STICKING_BILLBOARD", length = 10)
	private String stickingBillboard;

	@Column(name = "GARBAGECAN", length = 10)
	private String garbagecan;

	@Column(name = "COOLER_HEATER", length = 10)
	private String coolerHeater;

	@Column(name = "BRANCH_PERIPHERY", length = 10)
	private String branchPeriphery;


@Override
public Sh05001140Id getId() {
return Sh05001140Id.of(branchCode, cornerCode, overhaulDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh05001140Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private Timestamp overhaulDate;

}
}