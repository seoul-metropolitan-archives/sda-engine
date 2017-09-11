package com.bgf.shbank.domain.mng.etc.sh04001200;

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
@Table(name = "ATMS_04001200")
@Comment(value = "")
@IdClass(Sh04001200.Sh04001200Id.class)
@Alias("sh04001200")
public class Sh04001200 extends SimpleJpaModel<Sh04001200.Sh04001200Id> {

	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "CORP_CODE", length = 2)
	private String corpCode;

	@Id
	@Column(name = "INOUT_DATETIME", nullable = false)
	private Timestamp inoutDatetime;

	@Column(name = "INOUT_GUBUN", length = 1)
	private String inoutGubun;

	@Column(name = "TASK_GUBUN", length = 1)
	private String taskGubun;

	@Column(name = "AUTH_EMP_NO", length = 1)
	private String authEmpNo;


@Override
public Sh04001200Id getId() {
return Sh04001200Id.of(branchCode, cornerCode, inoutDatetime);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh04001200Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private Timestamp inoutDatetime;

}
}