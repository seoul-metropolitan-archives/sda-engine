package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
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
@Table(name = "AD_CON_META_SEGMENT")
@IdClass(AdConMetaSegment.AdConMetaSegmentId.class)
@Alias("AdConMetaSegment")
public class AdConMetaSegment extends SimpleJpaModel<AdConMetaSegment.AdConMetaSegmentId> {

	@Id
	@Column(name = "ADD_META_SEGMENT_UUID", length = 36, nullable = false)
	private String addMetaSegmentUuid;

	@Column(name = "ADD_META_TEMPLATE_SET_UUID", length = 36, nullable = false)
	private String addMetaTemplateSetUuid;

	@Column(name = "SEQUENCE", length = 22, nullable = false)
	private int sequence;

	@Column(name = "ENTITY_TYPE", length = 100, nullable = false)
	private String entityType;

	@Column(name = "TITLE", length = 500, nullable = false)
	private String title;

	@Column(name = "ADDITIONAL_COLUMN", length = 36, nullable = false)
	private String additionalColumn;

	@Column(name = "POPUP_UUID", length = 36)
	private String popupUuid;

	@Column(name = "DISPLAYED_YN", length = 1)
	private String displayedYN;

	@Column(name = "REQUIRED_YN", length = 1)
	private String requiredYN;

	@Column(name = "DISPLAY_SIZE", length = 22)
	private int displaySize;

	@Column(name = "USE_YN", length = 1)
	private String useYN;


	@Override
	public AdConMetaSegment.AdConMetaSegmentId getId() { return AdConMetaSegment.AdConMetaSegmentId.of(addMetaSegmentUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class AdConMetaSegmentId implements Serializable {

		@NonNull
		private String addMetaSegmentUuid;
	}
}