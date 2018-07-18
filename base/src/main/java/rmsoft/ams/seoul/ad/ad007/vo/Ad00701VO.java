package rmsoft.ams.seoul.ad.ad007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00701VO extends BaseVO {

	private String addContextualMetaUuid;

	private String statusUuid;

	private String entityType;

	private String columnCode;

	private String columnValue;

	private String metadataEntityType;

	private String additionalColumn;

	private String inputMethodUuid;

	private String inputValue;

	private String title;

	private String requiredYN;

	private String displayYN;

	private String useYN;

	private String changeStatus;

	private int metaCnt;
}