package rmsoft.ams.seoul.ad.ad010.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=true)
public class Ad01002VO extends BaseVO {
	private String noticeUuid;
	private String title;
	private String registerUuid;
	private String registerDate;
	private String contents;
	private String filePath;
	private String fileName;
}