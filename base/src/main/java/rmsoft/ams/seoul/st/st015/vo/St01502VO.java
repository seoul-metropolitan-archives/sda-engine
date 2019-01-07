package rmsoft.ams.seoul.st.st015.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01502VO extends BaseVO {

    private String inventoryContResultUuid;

    private String inventoryPlanUuid;

    private String containerUuid;

    private String inventoryResultUuid;

    private String containerStatusUuid;

}
