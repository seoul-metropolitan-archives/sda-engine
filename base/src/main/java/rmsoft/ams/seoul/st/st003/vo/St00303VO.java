package rmsoft.ams.seoul.st.st003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper=true)
public class St00303VO extends BaseVO {

	private String itemUuid;
	private String itemTitle;
	private String itemCode;
	private String levelUuid;
	private String aggregationUuid;
	private String publishedStatusUuid;
	private String aggregationCode;
	private String parentAggregationUuid;
	private String catPath;
	private String containerUuid;
	private String classUuid;
	private String recordScheduleUuid;
}