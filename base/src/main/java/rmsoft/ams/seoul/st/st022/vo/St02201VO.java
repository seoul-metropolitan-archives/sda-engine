package rmsoft.ams.seoul.st.st022.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St02201VO extends BaseVO {

    private String repositoryName;
    private String shelfName;
    private String locationName;
    private String container1;
    private String container2;
    private String container3;
    private String tag1;
    private String tag2;
    private String record1;
    private String record2;
    private String record3;
    private String record4;

    private String repositoryUuid;
    private String shelfUuid;
    private String locationUuid;
    private String containerUuid;

}
