package rmsoft.ams.seoul.lt.lt002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Lt00201VO extends BaseVO {

	private String softwareUuid;

	private String softwareName;

	private String softwareVersion;

	private String vendorName;

	private String vendorHomepage;

	private String licenseCode;

	private String installPath;

	private String exeFileName;

	private String description;

	private String useYN;
}