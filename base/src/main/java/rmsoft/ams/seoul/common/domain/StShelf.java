package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_SHELF")
@IdClass(StShelf.StShelfId.class)
@Alias("StShelf")
public class StShelf extends BaseJpaModel<StShelf.StShelfId>{

	@Id
	@Column(name = "SHELF_UUID", length = 36, nullable = false)
	private String shelfUuid;

	@Column(name = "REPOSITORY_UUID", length = 36, nullable = false)
	private String repositoryUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "SHELF_CODE", length = 30, nullable = false)
	private String shelfCode;

	@Column(name = "SHELF_NAME", length = 50, nullable = false)
	private String shelfName;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;

	@Override
	public StShelfId getId() {
		return StShelfId.of(shelfUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class StShelfId implements Serializable {
		@NonNull
		private String shelfUuid;
	}
}