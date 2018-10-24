package rmsoft.ams.seoul.ac.ac011.vo;

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
public class Ac011VO extends BaseVO {

	private String roleRowSecurityUuid;

	private String roleUuid;

	private String rowSecurityUuid;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Ac011VO of(Ac011 ac011) {
        Ac011VO ac011VO = ModelMapperUtils.map(ac011, Ac011VO.class);
        return ac011VO;
    }

    public static List<Ac011VO> of(List<Ac011> ac011List) {
        return ac011List.stream().map(ac011 -> of(ac011)).collect(toList());
    }

    public static List<Ac011VO> of(Page<Ac011> ac011Page) {
        return ac011Page.getContent().stream().map(ac011 -> of(ac011)).collect(toList());
    }
}