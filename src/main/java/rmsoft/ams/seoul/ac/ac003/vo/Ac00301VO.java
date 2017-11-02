package rmsoft.ams.seoul.ac.ac003.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import rmsoft.ams.seoul.common.domain.AcUser;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00301VO extends BaseVO {
    private String userUuid;

    private String userId;

    private String userNm;

    private String userPassword;

    private Timestamp passwordUpdateDate;

    private String userTypeUuid;

    private String startProgramUuid;

    private String startProgramName;

    private String organizationUuid;

    private String regDate;

    private String useYn;

    public static Ac00301VO of(AcUser acUser) {
        // Custom Mapper
        /*BoundMapperFacade<AcUser, Ac00301VO> mapper =
                ModelMapperUtils.getMapper(AcUser.class.getPackage().getName(), Ac00301VO.class.getPackage().getName());
        return mapper.map(acUser);*/
        return ModelMapperUtils.map(acUser, Ac00301VO.class);
    }

    public static List<Ac00301VO> of(List<AcUser> acUserList) {
        return acUserList.stream().map(acUser -> of(acUser)).collect(toList());
    }

    public static List<Ac00301VO> of(Page<AcUser> acUserPage) {
        return acUserPage.getContent().stream().map(acUser -> of(acUser)).collect(toList());
    }

}