package rmsoft.ams.seoul.rs.rs002.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class Rs002VO extends BaseVO {

	private String triggerUuid;

	private String statusUuid;

	private String triggerCode;

	private String triggerName;

	private Timestamp triggerDate;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Rs002VO of(Rs002 rs002) {
        Rs002VO rs002VO = ModelMapperUtils.map(rs002, Rs002VO.class);
        return rs002VO;
    }

    public static List<Rs002VO> of(List<Rs002> rs002List) {
        return rs002List.stream().map(rs002 -> of(rs002)).collect(toList());
    }

    public static List<Rs002VO> of(Page<Rs002> rs002Page) {
        return rs002Page.getContent().stream().map(rs002 -> of(rs002)).collect(toList());
    }
}