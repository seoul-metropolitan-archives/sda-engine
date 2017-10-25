package rmsoft.ams.seoul.ad.ad003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
public class Ad00301VO extends BaseVO
{
    private String codeHeaderUuid;
    private String categoryCode;
    private String categoryName;
    private String serviceUuid;
    private String codeTypeUuid;
    private String orderMethodUuid;
    private String useYN;
    private String attribute01;
    private String attribute02;
    private String attribute03;
    private String attribute04;
    private String attribute05;
    private String attribute06;
    private String attribute07;
    private String attribute08;
    private String attribute09;
    private String attribute10;
}
