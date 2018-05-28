package rmsoft.ams.seoul.rs.rs003.vo;

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
public class Rs003VO extends BaseVO {

	private String recordScheduleUuid;

	private String statusUuid;

	private String rsCode;

	private String rsName;

	private String generalRecordScheduleUuid;

	private String triggerUuid;

	private String retenionPeriodUuid;

	private String disposalTypeUuid;

	private String basedOn;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Rs003VO of(Rs003 rs003) {
        Rs003VO rs003VO = ModelMapperUtils.map(rs003, Rs003VO.class);
        return rs003VO;
    }

    public static List<Rs003VO> of(List<Rs003> rs003List) {
        return rs003List.stream().map(rs003 -> of(rs003)).collect(toList());
    }

    public static List<Rs003VO> of(Page<Rs003> rs003Page) {
        return rs003Page.getContent().stream().map(rs003 -> of(rs003)).collect(toList());
    }
}