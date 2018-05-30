package rmsoft.ams.seoul.ac.ac012.vo;

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
public class Ac012VO extends BaseVO {

	private String accessControlUuid;

	private String userUuid;

	private String userGroupUuid;

	private String roleUuid;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Ac012VO of(Ac012 ac012) {
        Ac012VO ac012VO = ModelMapperUtils.map(ac012, Ac012VO.class);
        return ac012VO;
    }

    public static List<Ac012VO> of(List<Ac012> ac012List) {
        return ac012List.stream().map(ac012 -> of(ac012)).collect(toList());
    }

    public static List<Ac012VO> of(Page<Ac012> ac012Page) {
        return ac012Page.getContent().stream().map(ac012 -> of(ac012)).collect(toList());
    }
}