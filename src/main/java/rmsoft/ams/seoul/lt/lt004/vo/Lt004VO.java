package rmsoft.ams.seoul.lt.lt004.vo;

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
public class Lt004VO extends BaseVO {

	private String pathwayUuid;

	private String pathwayName;

	private String sourceFileFormatUuid;

	private String targetFileFormatUuid;

	private String toolUuid;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Lt004VO of(Lt004 lt004) {
        Lt004VO lt004VO = ModelMapperUtils.map(lt004, Lt004VO.class);
        return lt004VO;
    }

    public static List<Lt004VO> of(List<Lt004> lt004List) {
        return lt004List.stream().map(lt004 -> of(lt004)).collect(toList());
    }

    public static List<Lt004VO> of(Page<Lt004> lt004Page) {
        return lt004Page.getContent().stream().map(lt004 -> of(lt004)).collect(toList());
    }
}