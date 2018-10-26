package rmsoft.ams.seoul.ad.ad007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00702VO extends BaseVO {

	private String addMetaSegmentUuid;

	private String addMetaTemplateSetUuid;

	private int sequence;

	private String name;

	private String title;

	private String additionalColumn;

	private String popupUuid;

	private String displayedYN;

	private String requiredYN;

	private int displaySize;

	private String useYN;
}