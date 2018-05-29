package rmsoft.ams.seoul.ad.ad008.vo;

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
public class Ad008VO extends BaseVO {

	private String auditUuid;

	private String entityTypeUuid;

	private String entityColumnUuid;

	private String programUuid;

	private String programName;

	private String functionUuid;

	private String primaryKeyUuid;

	private String previousValue;

	private String newValue;

	private String description;

	private String notes;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Ad008VO of(Ad008 ad008) {
        Ad008VO ad008VO = ModelMapperUtils.map(ad008, Ad008VO.class);
        return ad008VO;
    }

    public static List<Ad008VO> of(List<Ad008> ad008List) {
        return ad008List.stream().map(ad008 -> of(ad008)).collect(toList());
    }

    public static List<Ad008VO> of(Page<Ad008> ad008Page) {
        return ad008Page.getContent().stream().map(ad008 -> of(ad008)).collect(toList());
    }
}