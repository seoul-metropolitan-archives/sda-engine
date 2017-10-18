package rmsoft.ams.seoul.ac.ac003.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import ma.glasnost.orika.BoundMapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ac.ac003.dao.Ac003Mapper;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00303VO;
import rmsoft.ams.seoul.common.domain.AcUser;
import rmsoft.ams.seoul.common.domain.QAcUser;
import rmsoft.ams.seoul.common.repository.AcUserGroupUserRepository;
import rmsoft.ams.seoul.common.repository.AcUserRepository;

import javax.inject.Inject;

@Service
public class Ac003Service extends BaseService {

    @Autowired
    private AcUserRepository acUserRepository;

    @Autowired
    private AcUserGroupUserRepository acUserGroupUserRepository;

    @Inject
    private Ac003Mapper ac003Mapper;

    // USER 관련 호출부
    public Page<Ac00301VO> findAllUser(Pageable pageable, RequestParams<Ac00301VO> requestParams) {

        Ac00301VO ac00301VO = new Ac00301VO();
        ac00301VO.setUserId(requestParams.getString("userId"));
        ac00301VO.setUserNm(requestParams.getString("userNm"));
        ac00301VO.setUserTypeUuid(requestParams.getString("userTypeUuid"));
        ac00301VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac003Mapper.findAllUser(ac00301VO), pageable, "", Ac00301VO.class);
    }

    public AcUser findOne(RequestParams<Ac00301VO> requestParams) {
        QAcUser qAcUser = QAcUser.acUser;

        Predicate predicate = qAcUser.userUuid.eq(requestParams.getString("userUuid"));

        return acUserRepository.findOne(predicate);
    }

    // USER GROUP 관련 호출부
    public Page<Ac00302VO> findUserGroupUser(Pageable pageable, RequestParams<Ac00302VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(ac003Mapper.findUserGroupUserByUserUuid(requestParams.getString("userUuid")), pageable, filter, Ac00302VO.class);
    }

    // USER ROLE 관련 호출부
    public Page<Ac00303VO> findUserRole(Pageable pageable, RequestParams<Ac00303VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        Ac00303VO ac00303VO = new Ac00303VO();

        if (StringUtils.isNotEmpty(requestParams.getString("userUuid"))) {
            ac00303VO.setUserUuid(requestParams.getString("userUuid"));
        } else if (StringUtils.isNotEmpty(requestParams.getString("userGroupUuid"))) {
            ac00303VO.setUserGroupUuid(requestParams.getString("userGroupUuid"));
        }

        return filter(ac003Mapper.findUserRole(ac00303VO), pageable, filter, Ac00303VO.class);
    }

    @Transactional
    public void deleteUser(AcUser acUser) {
        acUserRepository.delete(acUser);
    }

    @Transactional
    public void saveUser(AcUser acUser) {
        acUserRepository.save(acUser);
    }

    private Ac00301VO buildVO(AcUser acUser) {

        if (acUser == null) {
            return new Ac00301VO();
        } else {
            BoundMapperFacade<AcUser, Ac00301VO> mapper =
                    ModelMapperUtils.getMapper("AcUser", this.getClass().getPackage().getName());
            return mapper.map(acUser);
        }
    }
}