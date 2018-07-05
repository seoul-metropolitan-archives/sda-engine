package rmsoft.ams.seoul.st.st002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type St00201vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00201VO extends BaseVO {
	private String containerUuid;
	private String statusUuid;
	private String containerName;
	private String containerTreeName;
	private String containerTypeUuid;
	private String parentContainerUuid;
	private String controlNumber;
	private String provenance;
	private String creationStartDate;
	private String creationEndDate;
	private String orderNo;
	private String orderKey;
	private String useYn;
	private String orderKey1;
	private String path;
	private String changeStatus;
	private String parentContainerName;
}