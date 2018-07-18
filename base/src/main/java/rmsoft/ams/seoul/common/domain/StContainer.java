package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.BaseJpaModel;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_CONTAINER")
@IdClass(StContainer.StContainerId.class)
@Alias("StContainer")
public class StContainer extends BaseJpaModel<StContainer.StContainerId> {

	@Id
	@Column(name = "CONTAINER_UUID", length = 36, nullable = false)
	private String containerUuid;

	@Column(name = "PARENT_CONTAINER_UUID", length = 36, nullable = false)
	private String parentContainerUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "CONTAINER_NAME", length = 50, nullable = false)
	private String containerName;

	@Column(name = "CONTAINER_TYPE_UUID", length = 36, nullable = false)
	private String containerTypeUuid;

	@Column(name = "CONTROL_NUMBER", length = 30, nullable = false)
	private String controlNumber;

	@Column(name = "PROVENANCE", length = 500)
	private String provenance;

	@Column(name = "CREATION_START_DATE", length = 8)
	private String creationStartDate;

	@Column(name = "CREATION_END_DATE", length = 8)
	private String creationEndDate;

	@Column(name = "ORDER_NO", precision = 0, scale = -127)
	private String orderNo;

	@Column(name = "ORDER_KEY", length = 100)
	private String orderKey;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;

	@Override
	public StContainerId getId() { return StContainerId.of(containerUuid); }

	/**
	 * The type Cl class id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class StContainerId implements Serializable {
		@NonNull
		private String containerUuid;
	}
}