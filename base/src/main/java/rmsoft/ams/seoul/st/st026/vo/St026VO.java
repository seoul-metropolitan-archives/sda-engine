package rmsoft.ams.seoul.st.st026.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@EqualsAndHashCode(callSuper=true)
public class St026VO extends BaseVO {

    private String rfidMachineUuid;
    private String no;
    private String machineTypeUuid;
    private String machineId;
    private String machineName;
    private String macAddr;
    private String ip;
    private String subnetmask;
    private String gateway;
    private String requestName;



    public static St026VO of(St026 st026) {
        St026VO st026VO = ModelMapperUtils.map(st026, St026VO.class);
        return st026VO;
    }

    public static List<St026VO> of(List<St026> st026List) {
        return st026List.stream().map(st026 -> of(st026)).collect(toList());
    }

    public static List<St026VO> of(Page<St026> st004Page) {
        return st004Page.getContent().stream().map(st026 -> of(st026)).collect(toList());
    }
}
