package com.bgf.shbank.domain.mng.etc.sh04001170;

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
@Table(name = "ATMS_04001170")
@Comment(value = "")
@IdClass(Sh04001170.Sh04001170Id.class)
@Alias("sh04001170")
public class Sh04001170 extends SimpleJpaModel<Sh04001170.Sh04001170Id> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "INFO_GUBUN", length = 1)
	private String infoGubun;

	@Column(name = "PARTITION_COUNT", length = 2)
	private String partitionCount;

	@Column(name = "DRIVE_GUBUN1", length = 1)
	private String driveGubun1;

	@Column(name = "TOTAL_LIMIT1", length = 7)
	private String totalLimit1;

	@Column(name = "USAGE1", length = 7)
	private String usage1;

	@Column(name = "DRIVE_GUBUN2", length = 1)
	private String driveGubun2;

	@Column(name = "TOTAL_LIMIT2", length = 7)
	private String totalLimit2;

	@Column(name = "USAGE2", length = 7)
	private String usage2;

	@Column(name = "DRIVE_GUBUN3", length = 1)
	private String driveGubun3;

	@Column(name = "TOTAL_LIMIT3", length = 7)
	private String totalLimit3;

	@Column(name = "USAGE3", length = 7)
	private String usage3;

	@Column(name = "DRIVE_GUBUN4", length = 1)
	private String driveGubun4;

	@Column(name = "TOTAL_LIMIT4", length = 7)
	private String totalLimit4;

	@Column(name = "USAGE4", length = 7)
	private String usage4;

	@Column(name = "DRIVE_GUBUN5", length = 1)
	private String driveGubun5;

	@Column(name = "TOTAL_LIMIT5", length = 7)
	private String totalLimit5;

	@Column(name = "USAGE5", length = 7)
	private String usage5;

	@Column(name = "DRIVE_GUBUN6", length = 1)
	private String driveGubun6;

	@Column(name = "TOTAL_LIMIT6", length = 7)
	private String totalLimit6;

	@Column(name = "USAGE6", length = 7)
	private String usage6;

	@Column(name = "CONTENTS", length = 300)
	private String contents;

	@Override
	public Sh04001170Id getId() {
	return Sh04001170Id.of(txId, branchCode, terminalNo);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh04001170Id implements Serializable {

			@NonNull
			private Timestamp txId;

			@NonNull
			private String branchCode;

			@NonNull
			private String terminalNo;
	}
}