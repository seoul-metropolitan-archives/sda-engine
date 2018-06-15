package rmsoft.ams.seoul.df.df001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper = false)
public class Df00102VO extends BaseVO {

	private String disposalFreezeEventUuid;
	private String freezeCnt;
	private String aggregationCnt;
	private String itemCnt;
}