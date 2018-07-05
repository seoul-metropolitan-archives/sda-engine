package rmsoft.ams.seoul.ad.ad008.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00801VO extends BaseVO {

	private String auditUuid;

	private String entityType;

	private String entityTypeName;

	private String entityColumn;

	private String programId;

	private String programName;

	private String function;

	private String primaryKey;

	private String previousValue;

	private String newValue;

	private String modifyDateFrom;

	private String modifyDateTo;

}