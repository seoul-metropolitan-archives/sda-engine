package rmsoft.ams.seoul.lt.lt003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Lt00301VO extends BaseVO {

	private String toolUuid;

	private String toolName;

	private String api;

	private String softwareUuid;

	private String softwareName;

	private String useYN;
}