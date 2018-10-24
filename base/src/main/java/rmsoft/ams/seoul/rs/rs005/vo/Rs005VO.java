package rmsoft.ams.seoul.rs.rs005.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@EqualsAndHashCode(callSuper=true)
public class Rs005VO extends BaseVO {

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


    public static Rs005VO of(Rs005 rs005) {
        Rs005VO rs005VO = ModelMapperUtils.map(rs005, Rs005VO.class);
        return rs005VO;
    }

    public static List<Rs005VO> of(List<Rs005> rs005List) {
        return rs005List.stream().map(rs005 -> of(rs005)).collect(toList());
    }

    public static List<Rs005VO> of(Page<Rs005> rs005Page) {
        return rs005Page.getContent().stream().map(rs005 -> of(rs005)).collect(toList());
    }
}