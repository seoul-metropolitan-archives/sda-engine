package rmsoft.ams.seoul.ad.ad008.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00801VO extends BaseVO {

	private String auditUuid;

	private String entityTypeUuid;

	private String entityColumnUuid;

	private String programUuid;

	private String programName;

	private String functionUuid;

	private String primaryKeyUuid;

	private String previousValue;

	private String newValue;

}