package rmsoft.ams.seoul.common.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Text column vo.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class FileColumnVO extends BaseColumnVO {
    private boolean editable = true;
}
