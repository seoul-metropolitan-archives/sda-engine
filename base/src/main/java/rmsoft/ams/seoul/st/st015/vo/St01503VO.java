package rmsoft.ams.seoul.st.st015.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01503VO extends BaseVO {

    private String inventoryRecordResultUuid;

    private String inventoryPlanUuid;

    private String containerUuid;

    private String aggregationUuid;

    private String workerUuid;

    private String inventoryDate;

    private String inventoryResultUuid;

    private String tagStatusUuid;

    private String recordStatusUuid;

    private String code;
    private String title;
    private String level;
    private String type;

}
