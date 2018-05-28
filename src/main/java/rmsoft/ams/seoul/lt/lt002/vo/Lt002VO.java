package rmsoft.ams.seoul.lt.lt002.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;
import java.sql.Timestamp;
import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class Lt002VO extends BaseVO {

	private String softwareUuid;

	private String softwareName;

	private String softwareVersion;

	private String vendorName;

	private String vendorHomepage;

	private String licenseCode;

	private String installPath;

	private String exeFileName;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Lt002VO of(Lt002 lt002) {
        Lt002VO lt002VO = ModelMapperUtils.map(lt002, Lt002VO.class);
        return lt002VO;
    }

    public static List<Lt002VO> of(List<Lt002> lt002List) {
        return lt002List.stream().map(lt002 -> of(lt002)).collect(toList());
    }

    public static List<Lt002VO> of(Page<Lt002> lt002Page) {
        return lt002Page.getContent().stream().map(lt002 -> of(lt002)).collect(toList());
    }
}