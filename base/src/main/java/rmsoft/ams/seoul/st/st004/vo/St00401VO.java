package rmsoft.ams.seoul.st.st004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper=true)
public class St00401VO extends BaseVO {
	private String arrangeContainersResultUuid;
	private String locationUuid;
	private String containerUuid;
	private String statusUuid;
	private String containerName;
	private String containerType;
	private String containerTypeName;
	private String controlNumber;
	private String containerTree;
	private String changeStatus;
	private Timestamp arrangedDate;
	private String orderKey1;
	private String choiceYn;
	private String parentContainerUuid;
	private String containerTypeUuid;
}