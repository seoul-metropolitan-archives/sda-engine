package rmsoft.ams.seoul.lt.lt004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Lt00401VO extends BaseVO {

	private String pathwayUuid;

	private String pathwayName;

	private String sourceFileFormatUuid;

	private String targetFileFormatUuid;

	private String toolUuid;

	private String sourceFileFormat;

	private String targetFileFormat;

	private String toolName;

	private String useYN;

}