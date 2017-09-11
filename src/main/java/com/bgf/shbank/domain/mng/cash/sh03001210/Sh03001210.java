package com.bgf.shbank.domain.mng.cash.sh03001210;

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
@Table(name = "ATMS_03001210")
@Comment(value = "")
@IdClass(Sh03001210.Sh03001210Id.class)
@Alias("sh03001210")
public class Sh03001210 extends SimpleJpaModel<Sh03001210.Sh03001210Id> {

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


@Override
public Sh03001210Id getId() {
return Sh03001210Id.of(txId, branchCode, terminalNo);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001210Id implements Serializable {

		@NonNull
		private Timestamp txId;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

}
}