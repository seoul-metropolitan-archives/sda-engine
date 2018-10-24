package rmsoft.ams.seoul.common.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The type Response for paging.
 *
 * @param <T> the type parameter
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ResponseForPaging<T> extends PageInfoVO {
    private List<T> list;
}
