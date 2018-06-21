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
@Table(name = "ST_LOCATION")
@IdClass(StLocation.StLocationId.class)
@Alias("StLocation")
public class StLocation extends BaseJpaModel<StLocation.StLocationId>{

	@Id
	@Column(name = "LOCATION_UUID", length = 36, nullable = false)
	private String locationUuid;

	@Column(name = "SHELF_UUID", length = 36, nullable = false)
	private String shelfUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "ROW_NO", length = 30, nullable = false)
	private String rowNo;

	@Column(name = "COLUMN_NO", length = 50, nullable = false)
	private String columnNo;

	@Column(name = "TOTAL_CONTAINER", length = 50, nullable = false)
	private String totalContainer;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;

	@Override
	public StLocationId getId() {
		return StLocationId.of(locationUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class StLocationId implements Serializable {
		@NonNull
		private String locationUuid;
	}
}