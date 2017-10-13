package rmsoft.ams.seoul.ac.ac003.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ac.ac003.dao.Ac003Mapper;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
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
    public Page<AcUser> findUser(Pageable pageable, String filter) {
        return filter(acUserRepository.findAll(), pageable, filter, AcUser.class);
    }

    public Ac00301VO findOne(RequestParams<Ac00301VO> requestParams) {
        QAcUser qAcUser = QAcUser.acUser;

        Predicate predicate = qAcUser.userId.eq(requestParams.getString("userId"));

        return buildVO(acUserRepository.findOne(predicate));
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

    // USER GROUP 관련 호출부
    public Page<Ac00302VO> findUserGroupUser(Pageable pageable, RequestParams<Ac00302VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(ac003Mapper.findUserGroupUserByUserUuid(requestParams.getString("userUuid")), pageable, filter, Ac00302VO.class);
    }

    // USER ROLE 관련 호출부
}