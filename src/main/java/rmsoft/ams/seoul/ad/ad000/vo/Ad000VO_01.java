package rmsoft.ams.seoul.ad.ad000.domain;

import io.onsemiro.core.vo.BaseVO;
import lombok.*;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ad000VO extends BaseVO
{
    private String service_uuid;
    private String service_code;
    private String service_name;
    private String install_yn;
}
