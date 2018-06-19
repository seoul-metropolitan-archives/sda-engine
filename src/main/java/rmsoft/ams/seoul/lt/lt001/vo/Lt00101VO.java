package rmsoft.ams.seoul.lt.lt001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Lt00101VO extends BaseVO {

	private String fileFormatUuid;

	private String puid;

	private String formatName;

	private String formatVersion;

	private String formatRisk;

	private String extension;

	private String formatGroupUuid;

	private String useYN;

}