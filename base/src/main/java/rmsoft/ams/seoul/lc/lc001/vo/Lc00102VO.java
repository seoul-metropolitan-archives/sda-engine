package rmsoft.ams.seoul.lc.lc001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper=true)
public class Lc00102VO extends BaseVO {
	private String leadCaseScheduleUuid;
	private String leadCaseUuid;
	private String scheduleNo;
	private String ingestOrgUuid;
	private String creatorUuid;
	private String collectionTypeUuid;
	private String contactTypeUuid;
	private String contactMaker;
	private String collectMaker;
	private Date collectionDate;
	private String collectionContents;
	private String description;
	private String notes;
	private String insertUuid;
	private Timestamp insertDate;
	private String updateUuid;
	private Timestamp updateDate;
}