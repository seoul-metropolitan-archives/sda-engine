package rmsoft.ams.seoul.ac.ac003.vo;

import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import rmsoft.ams.seoul.common.domain.AcUser;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac003VO_01 extends BaseVO {
    private String userUuid;

    private String userId;

    private String userNm;

    private String userPassword;

    private String passwordUpdateDate;

    private String userTypeUuid;

    private String startProgramUuid;

    private String organizationUuid;

    private String regDate;

    private AXBootTypes.Used useYn = AXBootTypes.Used.YES;

    public static Ac003VO_01 of(AcUser acUser) {
        // Custom Mapper
        /*BoundMapperFacade<AcUser, Ac003VO_01> mapper =
                ModelMapperUtils.getMapper("Ac003", Ac003VO_01.class.getPackage().getName());
        return mapper.map(acUser);*/
        return ModelMapperUtils.map(acUser, Ac003VO_01.class);
    }

    public static List<Ac003VO_01> of(List<AcUser> acUserList) {
        return acUserList.stream().map(acUser -> of(acUser)).collect(toList());
    }

    public static List<Ac003VO_01> of(Page<AcUser> acUserPage) {
        return acUserPage.getContent().stream().map(acUser -> of(acUser)).collect(toList());
    }

}