package rmsoft.ams.seoul.df.df003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper = false)
public class Df00302VO extends BaseVO {

	private String disposalFreezeResultUuid;
	private String disposalFreezeEventUuid;
	private String disposalFreezeDegreeUuid;
	private String aggregationTree;
	private String itemCode;
	private String title;
	private String itemUuid;
	private String statusUuid;
	private String description;
	private String notes;
	private Timestamp freezedDate;
	private String changeStatus;
	private String freezedToDate;
	private String freezedFromDate;

	// 아이템 추가시 사용
	private String itemTitle;
	private String levelUuid;
	private String aggregationUuid;
	private String publishedStatusUuid;
	private String aggregationCode;
	private String parentAggregationUuid;
	private String catPath;
	private String classUuid;
}