package rmsoft.ams.seoul.st.st018.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St018PrinterVO{


    private String aggregationUuid;
    private String aggregationCode; // 관리번호, 바코드
    private String authorityName; // 생산기관
    private String creationStartDate; // 생산년도
    private String retentionPeriodName; // 보존기간


}

