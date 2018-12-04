package rmsoft.ams.seoul.st.st012.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@EqualsAndHashCode(callSuper=true)
public class St012VO extends BaseVO {

	private String arrangeContainersResultUuid;

	private String locationUuid;

	private String containerUuid;



    public static rmsoft.ams.seoul.st.st012.vo.St012VO of(St012 st012) {
        rmsoft.ams.seoul.st.st012.vo.St012VO st012VO = ModelMapperUtils.map(st012, rmsoft.ams.seoul.st.st012.vo.St012VO.class);
        return st012VO;
    }

    public static List<rmsoft.ams.seoul.st.st012.vo.St012VO> of(List<St012> st012List) {
        return st012List.stream().map(st012 -> of(st012)).collect(toList());
    }

    public static List<rmsoft.ams.seoul.st.st012.vo.St012VO> of(Page<St012> st004Page) {
        return st004Page.getContent().stream().map(st012 -> of(st012)).collect(toList());
    }
}
