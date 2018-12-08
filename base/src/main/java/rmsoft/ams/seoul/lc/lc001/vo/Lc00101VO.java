package rmsoft.ams.seoul.lc.lc001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper=true)
public class Lc00101VO extends BaseVO {

	private String leadCaseUuid;
	private String leadCaseNo;
	private String leadCaseName;
	private String majorClassificationUuid;
	private String middleClassificationUuid;
	private String occupation;
	private String region;
	private String address;
	private String contactTarget;
	private String phone;
	private String personInCharge;
	private String collectStatusUuid;
	private String ownMaterial;
	private String description;
	private String notes;
	private String insertUuid;
	private Timestamp insertDate;
	private String updateUuid;
	private Timestamp updateDate;
	private int childCnt;
}