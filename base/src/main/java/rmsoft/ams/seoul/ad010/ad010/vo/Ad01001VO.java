package rmsoft.ams.seoul.ad010.ad010.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
public class Ad01001VO extends BaseVO {

	private String noticeUuid;

	private String no;

	private String title;

	private String registerUuid;

	private String registerDate;

	private String contents;

}