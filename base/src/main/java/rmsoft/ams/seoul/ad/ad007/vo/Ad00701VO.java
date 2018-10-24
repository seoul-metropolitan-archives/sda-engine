package rmsoft.ams.seoul.ad.ad007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00701VO extends BaseVO {

	private String addMetaTemplateSetUuid;

	private String entityType;

	private String setCode;

	private String setName;

	private String useYN;

	private String defaultYN;
}