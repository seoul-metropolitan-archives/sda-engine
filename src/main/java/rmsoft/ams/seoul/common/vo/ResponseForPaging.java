package rmsoft.ams.seoul.common.vo;


import lombok.Data;

import java.util.List;

@Data
public class ResponseForPaging<T> extends PageInfoVO {
    private List<T> list;
}
