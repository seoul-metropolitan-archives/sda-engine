package rmsoft.ams.seoul.df.df002.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Timestamp;
import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class Df002VO extends BaseVO {

	private String disposalFreezeDegreeUuid;

	private String disposalFreezeEventUuid;

	private String freezeYn;

	private BigDecimal degree;

	private String description;

	private String notes;

	private String endYn;

	private String terminatorUuid;

	private Timestamp endDate;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Df002VO of(Df002 df002) {
        Df002VO df002VO = ModelMapperUtils.map(df002, Df002VO.class);
        return df002VO;
    }

    public static List<Df002VO> of(List<Df002> df002List) {
        return df002List.stream().map(df002 -> of(df002)).collect(toList());
    }

    public static List<Df002VO> of(Page<Df002> df002Page) {
        return df002Page.getContent().stream().map(df002 -> of(df002)).collect(toList());
    }
}