package rmsoft.ams.seoul.st.st013.vo;

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
public class St013VO extends BaseVO {

    private String inoutExceptUuid;

    private String requestName;

    private String requestorUuid;

    private Timestamp requestDate;

    private Timestamp exceptStartDate;

    private Timestamp exceptEndDate;

    private String exceptReason;

    private String insertUuid;

    private Timestamp insertDate;

    private String updateUuid;

    private Timestamp updateDate;


    public static St013VO of(St013 st013) {
        St013VO st013VO = ModelMapperUtils.map(st013, St013VO.class);
        return st013VO;
    }

    public static List<St013VO> of(List<St013> st013List) {
        return st013List.stream().map(st013 -> of(st013)).collect(toList());
    }

    public static List<St013VO> of(Page<St013> st013Page) {
        return st013Page.getContent().stream().map(st013 -> of(st013)).collect(toList());
    }
}