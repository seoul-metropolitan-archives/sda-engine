package com.bgf.shbank.domain.mng.etc.sh04001180;

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
@Table(name = "ATMS_04001180")
@Comment(value = "")
@IdClass(Sh04001180.Sh04001180Id.class)
@Alias("sh04001180")
public class Sh04001180 extends SimpleJpaModel<Sh04001180.Sh04001180Id> {

	@Id
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

	@Column(name = "CRT_NO", length = 15)
	private String crtNo;


@Override
public Sh04001180Id getId() {
return Sh04001180Id.of(txId, branchCode, cornerCode);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh04001180Id implements Serializable {

		@NonNull
		private Timestamp txId;

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

}
}