package rmsoft.ams.seoul.common.vo;


import lombok.Data;

import java.util.List;

/**
 * The type Response for paging.
 *
 * @param <T> the type parameter
 */
@Data
public class ResponseForPaging<T> extends PageInfoVO {
    private List<T> list;
}
