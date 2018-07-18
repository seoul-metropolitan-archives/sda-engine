package rmsoft.ams.seoul.st.st003.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
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