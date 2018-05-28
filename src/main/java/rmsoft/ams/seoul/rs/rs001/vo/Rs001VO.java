package rmsoft.ams.seoul.rs.rs001.vo;

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
public class Rs001VO extends BaseVO {

	private String generalRecordScheduleUuid;

	private String statusUuid;

	private String grsCode;

	private String grsName;

	private String retenionPeriodUuid;

	private String disposalTypeUuid;

	private String basedOn;

	private String triggerYn;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Rs001VO of(Rs001 rs001) {
        Rs001VO rs001VO = ModelMapperUtils.map(rs001, Rs001VO.class);
        return rs001VO;
    }

    public static List<Rs001VO> of(List<Rs001> rs001List) {
        return rs001List.stream().map(rs001 -> of(rs001)).collect(toList());
    }

    public static List<Rs001VO> of(Page<Rs001> rs001Page) {
        return rs001Page.getContent().stream().map(rs001 -> of(rs001)).collect(toList());
    }
}