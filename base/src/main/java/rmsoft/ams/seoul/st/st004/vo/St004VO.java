package rmsoft.ams.seoul.st.st004.vo;

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
public class St004VO extends BaseVO {

	private String arrangeContainersResultUuid;

	private String locationUuid;

	private String containerUuid;

	private String statusUuid;

	private Timestamp arrangedDate;

	private String description;

	private String notes;

	private Timestamp updateDate;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;


    public static St004VO of(St004 st004) {
        St004VO st004VO = ModelMapperUtils.map(st004, St004VO.class);
        return st004VO;
    }

    public static List<St004VO> of(List<St004> st004List) {
        return st004List.stream().map(st004 -> of(st004)).collect(toList());
    }

    public static List<St004VO> of(Page<St004> st004Page) {
        return st004Page.getContent().stream().map(st004 -> of(st004)).collect(toList());
    }
}