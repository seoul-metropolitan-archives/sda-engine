package rmsoft.ams.seoul.st.st008.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Cl 00301 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00803VO extends BaseVO {
    private String containerUuid;
    private String itemUuid;
    private String itemTitle;
    private String itemCode;
    private String level;
    private String levelUuid;
    private String aggregationUuid;
    private String publishedStatusUuid;

    private String publishedStatus;
    private String author;
    private String startDate;
    private String endDate;
    private String code;
    private String title;
    private String type;
    private String typeUuid;
    private String itemTypeUuid;
 ;

    private String parentAggregationUuid;
    private String catPath;
}
