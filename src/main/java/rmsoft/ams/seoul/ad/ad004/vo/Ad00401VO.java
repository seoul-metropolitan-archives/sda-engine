package rmsoft.ams.seoul.ad.ad004.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.SessionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The type Ad 00401 vo.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00401VO extends BaseVO
{
    /**
     * Instantiates a new Ad 00401 vo.
     */
    public Ad00401VO()
    {
        this.insertUuid = this.updateUuid = SessionUtils.getCurrentUser().getUserUuid();
        this.insertDate = this.updateDate = Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
    }


    private String popupHeaderUuid  = "";

    private String popupCode        = "";

    private String popupName        = "";

    private String serviceUuid      = "";

    private String multiselectYN    = "Y";

    private String treeYN           = "N";

    private String description      = "";

    private String notes            = "";

    private String useYN            = "";

    private String popupSQL         = "";

}
