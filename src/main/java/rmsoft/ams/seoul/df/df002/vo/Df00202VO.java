package rmsoft.ams.seoul.df.df002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper = false)
public class Df00202VO extends BaseVO {

	private String disposalFreezeEventUuid;
	private String statusUuid;
	private String eventCode;
	private String eventName;
	private String eventTypeUuid;
	private String reviewDate;
	private String endYN;
	private String terminatorUuid;
	private Timestamp endDate;
	private String changeStatus;
	private String reviewDateTo;
	private String reason;
}