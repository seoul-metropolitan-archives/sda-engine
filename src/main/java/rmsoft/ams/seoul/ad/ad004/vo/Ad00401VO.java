package rmsoft.ams.seoul.ad.ad004.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.SessionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00401VO extends BaseVO
{

    public Ad00401VO()
    {
        this.insertUuid = this.updateUuid = SessionUtils.getCurrentLoginUserCd();
        this.insertDate = this.updateDate = Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
        this.updateUuid = SessionUtils.getCurrentLoginUserCd();
    }


    private String popupHeaderUUID  = "";

    private String popupCode        = "";

    private String popupName        = "";

    private String serviceUUID      = "";

    private String multiselectYN    = "Y";

    private String treeYN           = "N";

    private String description      = "";

    private String notes            = "";

    private String useYN            = "";

    private String popupSQL         = "";

}
