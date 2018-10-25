package rmsoft.ams.seoul.ad.ad009.vo;

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
public class Ad009VO extends BaseVO {

	private String auditUuid;

	private String entityTypeUuid;

	private String entityColumnUuid;

	private String programUuid;

	private String programName;

	private String functionUuid;

	private String primaryKeyUuid;

	private String previousValue;

	private String newValue;

	private String description;

	private String notes;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Ad009VO of(Ad009 ad009) {
        Ad009VO ad009VO = ModelMapperUtils.map(ad009, Ad009VO.class);
        return ad009VO;
    }

    public static List<Ad009VO> of(List<Ad009> ad009List) {
        return ad009List.stream().map(ad009 -> of(ad009)).collect(toList());
    }

    public static List<Ad009VO> of(Page<Ad009> ad009Page) {
        return ad009Page.getContent().stream().map(ad009 -> of(ad009)).collect(toList());
    }
}