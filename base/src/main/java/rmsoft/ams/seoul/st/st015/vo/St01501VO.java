package rmsoft.ams.seoul.st.st015.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01501VO extends BaseVO {

    private String inventoryPlanUuid;

    private String planName;

    private String plannerUuid;

    private String plannerName;

    private String exceptStartDateFrom;
    private String exceptStartDateTo;

    private String exceptEndDateFrom;
    private String exceptEndDateTo;

    private String exceptStartDate;
    private String exceptEndDate;

    private String repositoryUuid;

    private String repositoryName;

    private String shelfUuid;

    private String shelfName;

    private String locationUuid;

    private String locationName;

    private String statusUuid;

    private String planResultUuid;

    private String notes;

    private String exceptReason;

    private String changeStatus;

    private String code;

    private String title;

    private String statusName;

    private String planResultName;


}
