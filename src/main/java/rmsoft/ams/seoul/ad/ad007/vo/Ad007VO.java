package rmsoft.ams.seoul.ad.ad007.vo;

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
public class Ad007VO extends BaseVO {

	private String serviceUuid;

	private String serviceCode;

	private String serviceName;

	private String installYn;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Ad007VO of(Ad007 ad007) {
        Ad007VO ad007VO = ModelMapperUtils.map(ad007, Ad007VO.class);
        return ad007VO;
    }

    public static List<Ad007VO> of(List<Ad007> ad007List) {
        return ad007List.stream().map(ad007 -> of(ad007)).collect(toList());
    }

    public static List<Ad007VO> of(Page<Ad007> ad007Page) {
        return ad007Page.getContent().stream().map(ad007 -> of(ad007)).collect(toList());
    }
}