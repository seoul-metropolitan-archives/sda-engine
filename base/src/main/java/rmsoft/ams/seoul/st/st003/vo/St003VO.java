package rmsoft.ams.seoul.st.st003.vo;

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
public class St003VO extends BaseVO {

	private String arrangeRecordsResultUuid;

	private String containerUuid;

	private String aggregationUuid;

	private String itemUuid;

	private String statusUuid;

	private Timestamp arrangedDate;

	private String description;

	private String notes;

	private Timestamp updateDate;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;


    public static St003VO of(St003 st003) {
        St003VO st003VO = ModelMapperUtils.map(st003, St003VO.class);
        return st003VO;
    }

    public static List<St003VO> of(List<St003> st003List) {
        return st003List.stream().map(st003 -> of(st003)).collect(toList());
    }

    public static List<St003VO> of(Page<St003> st003Page) {
        return st003Page.getContent().stream().map(st003 -> of(st003)).collect(toList());
    }
}