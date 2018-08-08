package rmsoft.ams.seoul.df.df003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Df00301VO extends BaseVO {

	private String uuid;
	private String disposalFreezeEventUuid;
	private String disposalFreezeDegreeUuid;
	private int level;
	private String parentCode;
	private String title;
	private String keyword;
	private String orderKey1;
}