package rmsoft.ams.seoul.st.st003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper=true)
public class St00302VO extends BaseVO {

	private String aggregationUuid;
	private String publishedStatusUuid;
	private String aggregationCode;
	private String title;
	private String typeUuid;
	private String parentAggregationUuid;
	private String levelUuid;
	private String orderKey1;
	private String description;
	private String notes;
}