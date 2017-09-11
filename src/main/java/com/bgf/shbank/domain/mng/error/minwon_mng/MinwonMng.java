package com.bgf.shbank.domain.mng.error.minwon_mng;

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
@Table(name = "ATMS_MINWON_MNG")
@Comment(value = "")
@IdClass(MinwonMng.MinwonMngId.class)
@Alias("minwonMng")
public class MinwonMng extends SimpleJpaModel<MinwonMng.MinwonMngId> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Id
	@Column(name = "REG_DATETIME", nullable = false)
	private Timestamp regDatetime;

	@Column(name = "MINWON_TYPE", length = 2)
	private String minwonType;

	@Column(name = "MINWON_STATUS", length = 1)
	private String handleDept;

	@Column(name = "HANDLE_DEPT", length = 1)
	private String minwonStatus;

	@Column(name = "MINWON_CONTENT", length = 300)
	private String minwonContent;

	@Column(name = "HANDLE_CONTENT", length = 300)
	private String handleContent;

	@Column(name = "UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name = "LAST_MODIFY_EMP_NAME", length = 10)
	private String lastModifyEmpName;


@Override
public MinwonMngId getId() {
return MinwonMngId.of(branchCode, cornerCode, terminalNo, regDatetime);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class MinwonMngId implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp regDatetime;

}
}