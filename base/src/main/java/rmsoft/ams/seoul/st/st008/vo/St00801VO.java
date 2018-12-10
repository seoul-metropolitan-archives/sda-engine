package rmsoft.ams.seoul.st.st008.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Cl 00301 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00801VO extends BaseVO
{
    private String containerUuid;
    private String statusUuid;
    private String containerName;
    private String containerTypeUuid;
    private String parentContainerName;
    private String parentContainerUuid;
    private String controlNumber;
    private String provenance;
    private String creationStartDate;
    private String creationEndDate;
    private String orderNo;
    private String orderKey;
    private String orderKey1;
    private String description;
    private String notes;
    private String useYn;
}
