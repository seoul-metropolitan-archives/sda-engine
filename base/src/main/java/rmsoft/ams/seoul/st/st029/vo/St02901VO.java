package rmsoft.ams.seoul.st.st029.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St02901VO extends BaseVO {

    private String programUuid;

    private String machineTypeUuid;

    private String machineName;

    private String programName;

    private String version;

    private String filePath;

    private String fileName;

    private String description;

    private String registerUuid;
    private String registerName;

    private String startDate;
    private String endDate;

}
