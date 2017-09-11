package com.bgf.shbank.domain.mng.error.error_handle_mng;

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
@Table(name = "ATMS_ERROR_HANDLE_MNG")
@Comment(value = "")
@IdClass(ErrorHandleMng.ErrorHandleMngId.class)
@Alias("errorHandleMng")
public class ErrorHandleMng extends SimpleJpaModel<ErrorHandleMng.ErrorHandleMngId> {

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
	@Column(name = "ERROR_DATETIME", nullable = false)
	private Timestamp errorDatetime;

	@Column(name = "NOTICE_CONTENT", length = 300)
	private String noticeContent;

	@Column(name = "CUSTOMER_INFO", length = 300)
	private String customerInfo;

	@Column(name = "HANDLE_CONTENT", length = 300)
	private String handleContent;

	@Column(name = "LAST_MODIFY_DATETIME")
	private Timestamp lastModifyDatetime;

	@Column(name = "LAST_MODIFY_EMP_NAME", length = 10)
	private String lastModifyEmpName;


@Override
public ErrorHandleMngId getId() {
return ErrorHandleMngId.of(branchCode, cornerCode, terminalNo, errorDatetime);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class ErrorHandleMngId implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp errorDatetime;

}
}