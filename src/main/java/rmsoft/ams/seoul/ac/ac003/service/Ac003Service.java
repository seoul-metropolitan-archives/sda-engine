package rmsoft.ams.seoul.ac.ac003.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.domain.user.QUserGroupUser;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ac.ac003.dao.Ac003Mapper;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00303VO;
import rmsoft.ams.seoul.common.domain.AcAccessControl;
import rmsoft.ams.seoul.common.domain.AcUser;
import rmsoft.ams.seoul.common.domain.AcUserGroupUser;
import rmsoft.ams.seoul.common.domain.QAcAccessControl;
import rmsoft.ams.seoul.common.repository.AcAccessControlRepository;
import rmsoft.ams.seoul.common.repository.AcUserGroupUserRepository;
import rmsoft.ams.seoul.common.repository.AcUserRepository;

import javax.inject.Inject;
import java.util.List;

@Slf4j
@Service
public class Ac003Service extends BaseService {

    @Autowired
    private AcUserRepository acUserRepository;

    @Autowired
    private AcUserGroupUserRepository acUserGroupUserRepository;

    @Autowired
    private AcAccessControlRepository acAccessControlRepository;

    @Inject
    private Ac003Mapper ac003Mapper;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**********************************************************************************************
     * 사용자 정보 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 모든 사용자 조회
     *
     * @param pageable
     * @param requestParams
     * @return
     */
    public Page<Ac00301VO> findAllUser(Pageable pageable, RequestParams<Ac00301VO> requestParams) {

        Ac00301VO ac00301VO = new Ac00301VO();
        ac00301VO.setUserId(requestParams.getString("userId"));
        ac00301VO.setUserNm(requestParams.getString("userNm"));
        ac00301VO.setUserTypeUuid(requestParams.getString("userTypeUuid"));
        ac00301VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac003Mapper.findAllUser(ac00301VO), pageable, "", Ac00301VO.class);
    }

    /**
     * 사용자 정보 저장
     *
     * @param ac00301VOList
     * @return
     */
    @Transactional
    public ApiResponse saveUser(List<Ac00301VO> ac00301VOList) {
        List<AcUser> acUserList = ModelMapperUtils.mapList(ac00301VOList, AcUser.class);
        AcUser orgAcUser = null;

        for (AcUser acUser : acUserList) {
            orgAcUser = acUserRepository.findOne(acUser.getId());

            if (orgAcUser == null) {
                // created
                //acUser.setUserUuid(UUIDUtils.getUUID());
                acUser.setPasswordUpdateDate(DateUtils.getTimestampNow());
                acUser.setUserPassword(bCryptPasswordEncoder.encode(acUser.getUserPassword())); // 암호화

                acUserRepository.save(acUser);
            } else {
                if (acUser.isDeleted()) {
                    acUserRepository.delete(acUser);

                    // User가 삭제되므로, Access Control 도 모두 삭제
                    QAcAccessControl qAcAccessControl = QAcAccessControl.acAccessControl;
                    Predicate predicate = qAcAccessControl.userUuid.eq(acUser.getUserUuid());
                    acAccessControlRepository.delete(acAccessControlRepository.findAll(predicate));

                    // User가 삭제되므로, User Group User 도 모두 삭제
                    QUserGroupUser qUserGroupUser = QUserGroupUser.userGroupUser;
                    Predicate predicate1 = qUserGroupUser.userUuid.eq(acUser.getUserUuid());
                    acUserGroupUserRepository.delete(acUserGroupUserRepository.findAll(predicate1));

                } else {
                    if (!acUser.getUserPassword().equals(orgAcUser.getUserPassword())) {
                        // 비밀번호가 변경된 경우
                        acUser.setPasswordUpdateDate(DateUtils.getTimestampNow());
                        acUser.setUserPassword(bCryptPasswordEncoder.encode(acUser.getUserPassword())); // 암호화
                    } else {
                        acUser.setPasswordUpdateDate(orgAcUser.getPasswordUpdateDate());
                    }

                    acUser.setInsertDate(orgAcUser.getInsertDate());
                    acUser.setInsertUuid(orgAcUser.getInsertUuid());

                    acUserRepository.save(acUser);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**********************************************************************************************
     * 사용자 그룹 사용자 정보 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 그룹 사용자 조회
     *
     * @param pageable
     * @param requestParams
     * @return
     */
    // USER GROUP 관련 호출부
    public Page<Ac00302VO> findUserGroupUser(Pageable pageable, RequestParams<Ac00302VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(ac003Mapper.findUserGroupUserByUserUuid(requestParams.getString("userUuid")), pageable, filter, Ac00302VO.class);
    }

    /**
     * 그룹 사용자 정보 저장
     *
     * @param ac00302VOList
     * @return
     */
    @Transactional
    public ApiResponse saveUserGroup(List<Ac00302VO> ac00302VOList) {
        List<AcUserGroupUser> acUserGroupUserList = ModelMapperUtils.mapList(ac00302VOList, AcUserGroupUser.class);
        AcUserGroupUser orgAcUserGroupUser = null;

        for (AcUserGroupUser acUserGroupUser : acUserGroupUserList) {
            orgAcUserGroupUser = acUserGroupUserRepository.findOne(acUserGroupUser.getId());

            if (orgAcUserGroupUser == null) {
                // created
                acUserGroupUser.setUserGroupUserUuid(UUIDUtils.getUUID());
                acUserGroupUserRepository.save(acUserGroupUser);
            } else {
                if (acUserGroupUser.isDeleted()) {
                    acUserGroupUserRepository.delete(acUserGroupUser);
                } else {
                    acUserGroupUser.setInsertDate(orgAcUserGroupUser.getInsertDate());
                    acUserGroupUser.setInsertUuid(orgAcUserGroupUser.getInsertUuid());

                    acUserGroupUserRepository.save(acUserGroupUser);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**********************************************************************************************
     * 사용자 접근제어 정보 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 사용자 접근제어 정보 조회
     *
     * @param pageable
     * @param requestParams
     * @return
     */
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


    /**
     * 사용자 접근제어 정보 저장
     *
     * @param ac00303VOList
     * @return
     */
    @Transactional
    public ApiResponse saveUserRole(List<Ac00303VO> ac00303VOList) {
        List<AcAccessControl> acAccessControlList = ModelMapperUtils.mapList(ac00303VOList, AcAccessControl.class);
        AcAccessControl orgacAccessControl = null;


        for (AcAccessControl acAccessControl : acAccessControlList) {
            orgacAccessControl = acAccessControlRepository.findOne(acAccessControl.getId());

            if (orgacAccessControl == null) {
                // created
                acAccessControl.setAccessControlUuid(UUIDUtils.getUUID());
                acAccessControlRepository.save(acAccessControl);
            } else {
                if (acAccessControl.isDeleted()) {
                    acAccessControlRepository.delete(acAccessControl);
                } else {
                    acAccessControl.setInsertDate(orgacAccessControl.getInsertDate());
                    acAccessControl.setInsertUuid(orgacAccessControl.getInsertUuid());

                    acAccessControlRepository.save(acAccessControl);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }


    /**
     * VO 생성 Method
     *
     * @param acUser
     * @return
     */
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