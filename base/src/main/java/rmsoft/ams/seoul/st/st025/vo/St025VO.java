package rmsoft.ams.seoul.st.st025.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@EqualsAndHashCode(callSuper=true)
public class St025VO extends BaseVO {

    private String readerMachineUuid;

    private int no;

    private String machineId;

    private String machineName;

    private String gateUuid;

    private String ip;

    private String subnetmask;

    private String gateway;

    private int printDecrease;

    private String macAddr;

    private int antennaCnt;

    private String statusUuid;

    private String insertUuid;

    private Timestamp insertDate;

    private String updateUuid;

    private Timestamp updateDate;

    public static St025VO of(St025 st025){
        St025VO st025VO = ModelMapperUtils.map(st025, St025VO.class);
        return st025VO;
    }

    public static List<St025VO> of(List<St025> st025List){
        return st025List.stream().map(st025 -> of(st025)).collect(toList());
    }

    public static List<St025VO> of(Page<St025> st025Page){
        return st025Page.getContent().stream().map(st025 -> of(st025)).collect(toList());
    }
}
