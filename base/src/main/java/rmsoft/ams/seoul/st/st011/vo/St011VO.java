package rmsoft.ams.seoul.st.st011.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@EqualsAndHashCode(callSuper=true)
public class St011VO extends BaseVO {

	private String arrangeContainersResultUuid;

	private String locationUuid;

	private String containerUuid;



    public static St011VO of(St011 st011) {
        St011VO st011VO = ModelMapperUtils.map(st011, St011VO.class);
        return st011VO;
    }

    public static List<St011VO> of(List<St011> st011List) {
        return st011List.stream().map(st011 -> of(st011)).collect(toList());
    }

    public static List<St011VO> of(Page<St011> st004Page) {
        return st004Page.getContent().stream().map(st011 -> of(st011)).collect(toList());
    }
}