package rmsoft.ams.seoul.ad.ad004.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.SessionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00402VO extends BaseVO
{
    public Ad00402VO()
    {
        this.insertUuid = this.updateUuid = SessionUtils.getCurrentUser().getUserUuid();
        this.insertDate = this.updateDate = Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
    }

    private String popupDetailUUID = "";

    private String popupHeaderUUID = "";

    private String sqlColumn = "";

    private String title = "";

    private String width = "";

    private String inputMethodUUID = "";

    private String alignUUID = "";

    private String treeColumnYN = "";

    private String treeRelationUUID = "";

    private String orderNO = "";
}
