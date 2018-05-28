package rmsoft.ams.seoul.lt.lt001.vo;

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
public class Lt001VO extends BaseVO {

	private String fileFormatUuid;

	private String puid;

	private String formatName;

	private String formatVersion;

	private String formatRisk;

	private String extension;

	private String formatGroupUuid;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static Lt001VO of(Lt001 lt001) {
        Lt001VO lt001VO = ModelMapperUtils.map(lt001, Lt001VO.class);
        return lt001VO;
    }

    public static List<Lt001VO> of(List<Lt001> lt001List) {
        return lt001List.stream().map(lt001 -> of(lt001)).collect(toList());
    }

    public static List<Lt001VO> of(Page<Lt001> lt001Page) {
        return lt001Page.getContent().stream().map(lt001 -> of(lt001)).collect(toList());
    }
}