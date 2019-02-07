package rmsoft.ams.seoul.st.st023.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St02301VO extends BaseVO {
    private String repositoryName;
    private String shelfName;
    private String locationName;
    private String status1;
    private String status2;
    private String status3;
    private String status4;
    private String status5;

    private String repositoryUuid;
    private String shelfUuid;
    private String locationUuid;


}
