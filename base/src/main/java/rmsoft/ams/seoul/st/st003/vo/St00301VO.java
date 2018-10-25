package rmsoft.ams.seoul.st.st003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper=true)
public class St00301VO extends BaseVO {

	private String arrangeRecordsResultUuid;
	private String containerUuid;
	private String aggregationCode;
	private String title;
	private String typeUuid;
	private String aggregationTree;
	private String aggregationUuid;
	private String itemUuid;
	private String statusUuid;
	private String arrangedDate;
	private String arrangedFromDate;
	private String arrangedToDate;
	private String itemCode;
	private String changeStatus;
}