package rmsoft.ams.seoul.df.df002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
public class Df00201VO extends BaseVO {

	private String disposalFreezeDegreeUuid;
	private String disposalFreezeEventUuid;
	private String freezeYN;
	private String eventCode;
	private String eventName;
	private int degree;
	private String endYN;
	private String terminatorUuid;
	private Timestamp endDate;
	private String changeStatus;
	private String reviewDateTo;
}