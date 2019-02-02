package rmsoft.ams.seoul.st.st024.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St02402VO extends BaseVO {

    private String count1;
    private String count2;
    private String count3;
    private String count4;
    private String userName;

    private String userUuid;
    private String createDateFrom;
    private String createDateTo;
    private String code;
    private String title;
}
