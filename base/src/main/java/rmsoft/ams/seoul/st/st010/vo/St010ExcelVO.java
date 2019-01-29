package rmsoft.ams.seoul.st.st010.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St010ExcelVO {

    private String repositoryUuid;
    private String repositoryCode;
    private String repositoryName;
    private String shelfUuid;
    private String shelfCode;
    private String shelfName;
    private String locationUuid;
    private String rowNo;
    private String columnNo;
    private String aggregationCount;
    private String inCount;
    private String outCount;

    private String shelfCount;
    private String rowCount;


}
