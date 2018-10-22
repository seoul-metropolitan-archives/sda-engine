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
@Table(name = "AD_CON_META_SETUP")
@IdClass(AdConMetaSetup.AdConMetaSetupId.class)
@Alias("AdConMetaSetup")
public class AdConMetaSetup extends BaseJpaModel<AdConMetaSetup.AdConMetaSetupId> {

	@Id
	@Column(name = "ADD_META_TEMPLATE_SET_UUID", length = 36, nullable = false)
	private String addMetaTemplateSetUuid;

	@Column(name = "ENTITY_TYPE", length = 36, nullable = false)
	private String entityType;

	@Column(name = "SET_CODE", length = 30, nullable = false)
	private String setCode;

	@Column(name = "SET_NAME", length = 50)
	private String setName;

	@Column(name = "USE_YN", length = 1)
	private String useYN;

	@Column(name = "DEFAULT_YN", length = 1)
	private String defaultYN;


	@Override
	public AdConMetaSetup.AdConMetaSetupId getId() { return AdConMetaSetup.AdConMetaSetupId.of(addMetaTemplateSetUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class AdConMetaSetupId implements Serializable {

		@NonNull
		private String addMetaTemplateSetUuid;
	}
}