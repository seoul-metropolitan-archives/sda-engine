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
@Table(name = "AD_CONTEXTUAL_METADATA_SETUP")
@IdClass(AdContextualMeta.AdContextualMetaId.class)
@Alias("AdContextualMetadataSetup")
public class AdContextualMeta extends BaseJpaModel<AdContextualMeta.AdContextualMetaId> {

	@Id
	@Column(name = "ADD_CONTEXTUAL_META_UUID", length = 36, nullable = false)
	private String addContextualMetaUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "ENTITY_TYPE", length = 36)
	private String entityType;

	@Column(name = "COLUMN_CODE", length = 36, nullable = false)
	private String columnCode;

	@Column(name = "COLUMN_VALUE", length = 500)
	private String columnValue;

	@Column(name = "METADATA_ENTITY_TYPE", length = 500)
	private String metadataEntityType;

	@Column(name = "ADDITIONAL_COLUMN", length = 30)
	private String additionalColumn;

	@Column(name = "INPUT_METHOD_UUID", length = 36)
	private String inputMethodUuid;

	@Column(name = "INPUT_VALUE", length = 500)
	private String inputValue;

	@Column(name = "TITLE", length = 500)
	private String title;

	@Column(name = "REQUIRED_YN", length = 1)
	private String requiredYN;

	@Column(name = "DISPLAY_YN", length = 1)
	private String displayYN;

	@Column(name = "USE_YN", length = 1)
	private String useYN;



	@Override
	public AdContextualMeta.AdContextualMetaId getId() { return AdContextualMeta.AdContextualMetaId.of(addContextualMetaUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class AdContextualMetaId implements Serializable {

		@NonNull
		private String addContextualMetaUuid;
	}
}