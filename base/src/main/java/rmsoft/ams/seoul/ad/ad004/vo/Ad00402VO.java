package rmsoft.ams.seoul.ad.ad004.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.SessionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The type Ad 00402 vo.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00402VO extends BaseVO
{
    /**
     * Instantiates a new Ad 00402 vo.
     */
    public Ad00402VO()
    {
        this.insertUuid = this.updateUuid = SessionUtils.getCurrentUser().getUserUuid();
        this.insertDate = this.updateDate = Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
    }

    private String popupDetailUuid = "";

    private String popupHeaderUuid = "";

    private String sqlColumn = "";

    private String title = "";

    private String width = "";

    private String inputMethodUuid = "";

    private String alignUuid = "";

    private String treeColumnYN = "";

    private String treeRelationUuid = "";

    private String orderNO = "";
}
