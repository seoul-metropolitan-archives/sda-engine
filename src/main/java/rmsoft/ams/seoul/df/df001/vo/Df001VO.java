package rmsoft.ams.seoul.df.df001.vo;

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
public class Df001VO extends BaseVO {

	private String disposalFreezeEventUuid;

	private String statusUuid;

	private String eventCode;

	private String eventName;

	private String eventTypeUuid;

	private Timestamp reviewDate;

	private String description;

	private String notes;

	private String endYn;

	private String terminatorUuid;

	private Timestamp endDate;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Df001VO of(Df001 df001) {
        Df001VO df001VO = ModelMapperUtils.map(df001, Df001VO.class);
        return df001VO;
    }

    public static List<Df001VO> of(List<Df001> df001List) {
        return df001List.stream().map(df001 -> of(df001)).collect(toList());
    }

    public static List<Df001VO> of(Page<Df001> df001Page) {
        return df001Page.getContent().stream().map(df001 -> of(df001)).collect(toList());
    }
}