package rmsoft.ams.seoul.lt.lt003.vo;

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
public class Lt003VO extends BaseVO {

	private String toolUuid;

	private String toolName;

	private String api;

	private String sofwareUuid;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Lt003VO of(Lt003 lt003) {
        Lt003VO lt003VO = ModelMapperUtils.map(lt003, Lt003VO.class);
        return lt003VO;
    }

    public static List<Lt003VO> of(List<Lt003> lt003List) {
        return lt003List.stream().map(lt003 -> of(lt003)).collect(toList());
    }

    public static List<Lt003VO> of(Page<Lt003> lt003Page) {
        return lt003Page.getContent().stream().map(lt003 -> of(lt003)).collect(toList());
    }
}