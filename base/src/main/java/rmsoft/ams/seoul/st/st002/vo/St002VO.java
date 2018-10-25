package rmsoft.ams.seoul.st.st002.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@EqualsAndHashCode(callSuper=true)
public class St002VO extends BaseVO {

	private String containerUuid;

	private String statusUuid;

	private String containerName;

	private String containerTypeUuid;

	private String controlNumber;

	private String provenance;

	private String creationStartDate;

	private String creationEndDate;

	private BigDecimal orderNo;

	private String orderKey;

	private String description;

	private String notes;

	private String useYn;

	private String insertUuid;

	private Timestamp insertDate;

	private String updateUuid;

	private Timestamp updateDate;


    public static St002VO of(St002 st002) {
        St002VO st002VO = ModelMapperUtils.map(st002, St002VO.class);
        return st002VO;
    }

    public static List<St002VO> of(List<St002> st002List) {
        return st002List.stream().map(st002 -> of(st002)).collect(toList());
    }

    public static List<St002VO> of(Page<St002> st002Page) {
        return st002Page.getContent().stream().map(st002 -> of(st002)).collect(toList());
    }
}