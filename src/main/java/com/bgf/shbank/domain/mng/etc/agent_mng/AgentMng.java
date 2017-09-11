package com.bgf.shbank.domain.mng.etc.agent_mng;

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
@Table(name = "ATMS_AGENT_MNG")
@Comment("")
@Alias("agentMng")
public class AgentMng extends SimpleJpaModel<AgentMng.AgentMngId> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "EMP_REGNO", length = 13, nullable = false)
	private String empRegno;

	@Column(name = "EMP_NAME", length = 20, nullable = false)
	private String empName;

	@Column(name = "EMP_ENABLE", length = 1, nullable = false)
	private String empEnable;

	@Column(name = "EMP_GUBUN", length = 1)
	private String empGubun;

	@Column(name = "CORP_GUBUN", length = 1)
	private String corpGubun;

	@Column(name = "EMP_PHONE_NO", length = 20)
	private String empPhoneNo;

	@Column(name = "DIGITAL_SEAL_URL", length = 100)
	private String digitalSealUrl;

	@Column(name = "DIGITAL_SIGN_URL", length = 100)
	private String digitalSignUrl;

	@Override
	public AgentMngId getId() {
		return AgentMngId.of(txId);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class AgentMngId implements Serializable {

		@NonNull
		private Timestamp txId;
	}
}