/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac004.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.domain.user.QUserGroupUser;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ac.ac004.dao.Ac004Mapper;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00401VO;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00402VO;
import rmsoft.ams.seoul.ac.ac004.vo.Ac00403VO;
import rmsoft.ams.seoul.common.domain.AcAccessControl;
import rmsoft.ams.seoul.common.domain.AcUserGroup;
import rmsoft.ams.seoul.common.domain.AcUserGroupUser;
import rmsoft.ams.seoul.common.domain.QAcAccessControl;
import rmsoft.ams.seoul.common.repository.AcAccessControlRepository;
import rmsoft.ams.seoul.common.repository.AcUserGroupRepository;
import rmsoft.ams.seoul.common.repository.AcUserGroupUserRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 004 service.
 */
@Slf4j
@Service
public class Ac004Service extends BaseService {

    @Autowired
    private AcUserGroupRepository acUserGroupRepository;

    @Autowired
    private AcUserGroupUserRepository acUserGroupUserRepository;

    @Autowired
    private AcAccessControlRepository acAccessControlRepository;

    @Inject
    private Ac004Mapper ac004Mapper;

    /**********************************************************************************************
     * 사용자 정보 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 모든 그룹 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00401VO> findAllGroup(Pageable pageable, RequestParams<Ac00401VO> requestParams) {

        Ac00401VO ac00401VO = new Ac00401VO();
        ac00401VO.setUserGroupName(requestParams.getString("userGroupName"));
        ac00401VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac004Mapper.findAllGroup(ac00401VO), pageable, "", Ac00401VO.class);
    }

    /**
     * 그룹 정보 저장
     *
     * @param ac00401VOList the ac 00401 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveGroup(List<Ac00401VO> ac00401VOList) {
        List<AcUserGroup> acUserGroupList = ModelMapperUtils.mapList(ac00401VOList, AcUserGroup.class);
        AcUserGroup orgAcUserGroup = null;

        for (AcUserGroup acUserGroup : acUserGroupList) {
            orgAcUserGroup = acUserGroupRepository.findOne(acUserGroup.getId());

            if (orgAcUserGroup == null) {
                // created
                //acUserGroup.setUserGroupUuid(UUIDUtils.getUUID());
                acUserGroupRepository.save(acUserGroup);
            } else {
                if (acUserGroup.isDeleted()) {
                    acUserGroupRepository.delete(acUserGroup);

                    // User Group이 삭제되므로, Access Control 도 모두 삭제
                    QAcAccessControl qAcAccessControl = QAcAccessControl.acAccessControl;
                    Predicate predicate = qAcAccessControl.userGroupUuid.eq(acUserGroup.getUserGroupUuid());
                    acAccessControlRepository.delete(acAccessControlRepository.findAll(predicate));

                    // User Group이, User Group User 도 모두 삭제
                    QUserGroupUser qUserGroupUser = QUserGroupUser.userGroupUser;
                    Predicate predicate1 = qUserGroupUser.userGroupUserUuid.eq(acUserGroup.getUserGroupUuid());
                    acUserGroupUserRepository.delete(acUserGroupUserRepository.findAll(predicate1));

                } else {
                    acUserGroup.setInsertDate(orgAcUserGroup.getInsertDate());
                    acUserGroup.setInsertUuid(orgAcUserGroup.getInsertUuid());

                    acUserGroupRepository.save(acUserGroup);
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
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
// USER GROUP 관련 호출부
    public Page<Ac00402VO> findUserGroupUser(Pageable pageable, RequestParams<Ac00402VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(ac004Mapper.findUserGroupUserByUserGroupUuid(requestParams.getString("userGroupUuid")), pageable, filter, Ac00402VO.class);
    }

    /**
     * 그룹 사용자 정보 저장
     *
     * @param ac00402VOList the ac 00402 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveUserGroup(List<Ac00402VO> ac00402VOList) {
        List<AcUserGroupUser> acUserGroupUserList = ModelMapperUtils.mapList(ac00402VOList, AcUserGroupUser.class);
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
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00403VO> findUserRole(Pageable pageable, RequestParams<Ac00403VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        Ac00403VO ac00403VO = new Ac00403VO();

        if (StringUtils.isNotEmpty(requestParams.getString("userUuid"))) {
            ac00403VO.setUserUuid(requestParams.getString("userUuid"));
        } else if (StringUtils.isNotEmpty(requestParams.getString("userGroupUuid"))) {
            ac00403VO.setUserGroupUuid(requestParams.getString("userGroupUuid"));
        }

        return filter(ac004Mapper.findUserRole(ac00403VO), pageable, filter, Ac00403VO.class);
    }


    /**
     * 사용자 접근제어 정보 저장
     *
     * @param ac00403VOList the ac 00403 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveUserRole(List<Ac00403VO> ac00403VOList) {
        List<AcAccessControl> acAccessControlList = ModelMapperUtils.mapList(ac00403VOList, AcAccessControl.class);
        AcAccessControl orgAcAccessControl = null;

        for (AcAccessControl acAccessControl : acAccessControlList) {
            orgAcAccessControl = acAccessControlRepository.findOne(acAccessControl.getId());

            if (orgAcAccessControl == null) {
                // created
                acAccessControl.setAccessControlUuid(UUIDUtils.getUUID());
                acAccessControlRepository.save(acAccessControl);
            } else {
                if (acAccessControl.isDeleted()) {
                    acAccessControlRepository.delete(acAccessControl);
                } else {
                    acAccessControl.setInsertDate(orgAcAccessControl.getInsertDate());
                    acAccessControl.setInsertUuid(orgAcAccessControl.getInsertUuid());

                    acAccessControlRepository.save(acAccessControl);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * VO 생성 Method
     *
     * @param acUserGroup
     * @return
     */
    private Ac00401VO buildVO(AcUserGroup acUserGroup) {

        if (acUserGroup == null) {
            return new Ac00401VO();
        } else {
            BoundMapperFacade<AcUserGroup, Ac00401VO> mapper =
                    ModelMapperUtils.getMapper("AcUserGroup", this.getClass().getPackage().getName());
            return mapper.map(acUserGroup);
        }
    }
}