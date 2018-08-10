package rmsoft.ams.seoul.st.st001.vo;

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
public class St001VO extends BaseVO {

	private String repositoryUuid;

	private String statusUuid;

	private String repositoryCode;

	private String repositoryName;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static St001VO of(St001 st001) {
        St001VO st001VO = ModelMapperUtils.map(st001, St001VO.class);
        return st001VO;
    }

    public static List<St001VO> of(List<St001> st001List) {
        return st001List.stream().map(st001 -> of(st001)).collect(toList());
    }

    public static List<St001VO> of(Page<St001> st001Page) {
        return st001Page.getContent().stream().map(st001 -> of(st001)).collect(toList());
    }
}