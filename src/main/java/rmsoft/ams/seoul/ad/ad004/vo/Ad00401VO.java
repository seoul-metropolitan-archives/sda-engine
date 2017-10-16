package rmsoft.ams.seoul.ad.ad004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ad00401VO extends BaseVO
{
    private String popupHeaderUUID;

    private String popupCode;

    private String popupName;

    private String serviceUUID;

    private String multiselectYN;

    private String treeYN;

    private String description;

    private String note;

    private String useYN;

    private String popupSQL;
}
