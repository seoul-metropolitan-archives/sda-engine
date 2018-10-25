package rmsoft.ams.seoul.ac.ac010.vo;

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
public class Ac010VO extends BaseVO {

	private String rowSecurityUuid;

	private String rsEntityTypeUuid;

	private String rsEntityColumnUuid;

	private String fromValue;

	private String toValue;

	private String selectYn;

	private String insertYn;

	private String updateYn;

	private String deleteYn;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Ac010VO of(Ac010 ac010) {
        Ac010VO ac010VO = ModelMapperUtils.map(ac010, Ac010VO.class);
        return ac010VO;
    }

    public static List<Ac010VO> of(List<Ac010> ac010List) {
        return ac010List.stream().map(ac010 -> of(ac010)).collect(toList());
    }

    public static List<Ac010VO> of(Page<Ac010> ac010Page) {
        return ac010Page.getContent().stream().map(ac010 -> of(ac010)).collect(toList());
    }
}