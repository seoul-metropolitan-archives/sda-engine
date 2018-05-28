package rmsoft.ams.seoul.df.df003.vo;

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
public class Df003VO extends BaseVO {

	private String disposalFreezeResultUuid;

	private String disposalFreezeEventUuid;

	private String disposalFreezeDegreeUuid;

	private String aggregationId;

	private String itemUuid;

	private String statusUuid;

	private String description;

	private String notes;

	private Timestamp freezedDate;

	private String terminatorUuid;

	private Timestamp endDate;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Df003VO of(Df003 df003) {
        Df003VO df003VO = ModelMapperUtils.map(df003, Df003VO.class);
        return df003VO;
    }

    public static List<Df003VO> of(List<Df003> df003List) {
        return df003List.stream().map(df003 -> of(df003)).collect(toList());
    }

    public static List<Df003VO> of(Page<Df003> df003Page) {
        return df003Page.getContent().stream().map(df003 -> of(df003)).collect(toList());
    }
}