package rmsoft.ams.seoul.st.st027.vo;

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
public class St027VO extends BaseVO {

    private String zoneUuid;

    private int no;

    private String zoneId;

    private String zoneName;

    private String insertUuid;

    private Timestamp insertDate;

    private String updateUuid;

    private Timestamp updateDate;

    public static St027VO of(St027 st027){
        St027VO st027VO = ModelMapperUtils.map(st027, St027VO.class);
        return st027VO;
    }

    public static List<St027VO> of(List<St027> st027List){
        return st027List.stream().map(st027 -> of(st027)).collect(toList());
    }

    public static List<St027VO> of(Page<St027> st027Page){
        return st027Page.getContent().stream().map(st027 -> of(st027)).collect(toList());
    }

}
