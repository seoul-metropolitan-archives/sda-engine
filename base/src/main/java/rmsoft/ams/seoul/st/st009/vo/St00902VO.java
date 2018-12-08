package rmsoft.ams.seoul.st.st009.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00902VO extends BaseVO {

    private String takeoutRecordResultUuid;
    private String takeoutRequestUuid;
    private String aggregationUuid;
}
