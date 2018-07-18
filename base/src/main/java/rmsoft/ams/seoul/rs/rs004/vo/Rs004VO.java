package rmsoft.ams.seoul.rs.rs004.vo;

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
public class Rs004VO extends BaseVO {

	private String recordScheduleResultUuid;

	private String recordScheduleUuid;

	private String statusUuid;

	private String itemUuid;

	private String disposalTypeUuid;

	private Timestamp initialDate;

	private Timestamp disposalDueDate;

	private Timestamp disposalConfirmDate;

	private String disposalConfirmReason;

	private Timestamp disposalCompleteDate;

	private String description;

	private String notes;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Rs004VO of(Rs004 rs004) {
        Rs004VO rs004VO = ModelMapperUtils.map(rs004, Rs004VO.class);
        return rs004VO;
    }

    public static List<Rs004VO> of(List<Rs004> rs004List) {
        return rs004List.stream().map(rs004 -> of(rs004)).collect(toList());
    }

    public static List<Rs004VO> of(Page<Rs004> rs004Page) {
        return rs004Page.getContent().stream().map(rs004 -> of(rs004)).collect(toList());
    }
}