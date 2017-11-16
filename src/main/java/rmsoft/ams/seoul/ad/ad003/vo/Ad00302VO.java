package rmsoft.ams.seoul.ad.ad003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ad00302VO extends BaseVO
{
    private String codeDetailUuid;
    private String codeHeaderUuid;
    private String code;
    private String codeName;
    private String defaultYN = "N";
    private String orderNO;
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
