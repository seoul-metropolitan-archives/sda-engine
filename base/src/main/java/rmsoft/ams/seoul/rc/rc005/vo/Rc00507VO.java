package rmsoft.ams.seoul.rc.rc005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGR_RELATED_RECORD
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00507VO extends BaseVO
{
    private String jobid;
    private int seq;
    private String mergefile;
    private String page;
}
