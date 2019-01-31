package rmsoft.ams.seoul.st.st011.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01101VO {

    private String uuid;
    private String publishedStatus;
    private String code;
    private String title;
    private String type;
    private String level;
    private String author;
    private String descStrDate;
    private String descEdDate;
    private String repositoryName;
    private String shelfName;
    private String locationName;

    private String containerUuid;
    private String repositoryUuid;
    private String shelfUuid;
    private String locationUuid;

    private String requestorUuid;


}
