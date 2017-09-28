package rmsoft.ams.seoul.ac.ac003.domain;

import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac003VO extends BaseVO {
    private String userUuid;

    private String userId;

    private String userNm;

    private String userPassword;

    private Instant passwordUpdateDate;

    private String userTypeUuid;

    private String startProgramUuid;

    private String organizationUuid;

    private String regDate;

    private AXBootTypes.Used useYn = AXBootTypes.Used.YES;

    public static Ac003VO of(Ac003 ac003) {
        // Custom Mapper
        BoundMapperFacade<Ac003, Ac003VO> mapper =
                ModelMapperUtils.getMapper("Ac003", Ac003VO.class.getPackage().getName());
        Ac003VO ac003VO = mapper.map(ac003);

        return ac003VO;

        // Default Mapper
        //BoundMapperFacade<Ac003, Ac003VO> mapper = ModelMapperUtils.getMapper("Ac003", Ac003VO.class.getPackage().getName());
        //return mapper.map(Ac003);

    }

    public static List<Ac003VO> of(List<Ac003> ac003List) {
        return ac003List.stream().map(ac003 -> of(ac003)).collect(toList());
    }

    public static List<Ac003VO> of(Page<Ac003> ac003Page) {
        return ac003Page.getContent().stream().map(ac003 -> of(ac003)).collect(toList());
    }

}