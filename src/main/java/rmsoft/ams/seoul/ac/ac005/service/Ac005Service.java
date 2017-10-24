/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac005.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ac.ac005.dao.Ac005Mapper;
import rmsoft.ams.seoul.ac.ac005.vo.Ac00501VO;
import rmsoft.ams.seoul.ac.ac005.vo.Ac00502VO;
import rmsoft.ams.seoul.common.domain.AcRole;
import rmsoft.ams.seoul.common.domain.AcRolePermission;
import rmsoft.ams.seoul.common.domain.QAcRole;
import rmsoft.ams.seoul.common.domain.QAcRolePermission;
import rmsoft.ams.seoul.common.repository.AcRolePermissionRepository;
import rmsoft.ams.seoul.common.repository.AcRoleRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 005 service.
 */
@Slf4j
@Service
public class Ac005Service extends BaseService {

    @Autowired
    private AcRoleRepository acRoleRepository;

    @Autowired
    private AcRolePermissionRepository acRolePermissionRepository;

    @Inject
    private Ac005Mapper ac005Mapper;

    /**********************************************************************************************
     * 롤 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 모든 롤 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00501VO> findAllRole(Pageable pageable, RequestParams<Ac00501VO> requestParams) {

        Ac00501VO ac00501VO = new Ac00501VO();
        ac00501VO.setRoleName(requestParams.getString("roleName"));
        ac00501VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac005Mapper.findAllRole(ac00501VO), pageable, "", Ac00501VO.class);
    }

    /**
     * 그룹 정보 저장
     *
     * @param ac00501VOList the ac 00501 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveRole(List<Ac00501VO> ac00501VOList) {
        for (Ac00501VO ac00501VO : ac00501VOList) {
            AcRole acRole = ModelMapperUtils.map(ac00501VO, AcRole.class);
            AcRole orgAcRole = findOneRole(ac00501VO);

            if (orgAcRole == null) {
                // created
                acRole.setRoleUuid(UUIDUtils.getUUID());
                acRoleRepository.save(acRole);
            } else {
                if (ac00501VO.isDeleted()) {
                    acRoleRepository.delete(acRole);
                } else {
                    acRole.setInsertDate(orgAcRole.getInsertDate());
                    acRole.setInsertUuid(orgAcRole.getInsertUuid());

                    acRoleRepository.save(acRole);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * 단일 Role 조회
     *
     * @param requestParams the request params
     * @return ac user
     */
    public AcRole findOneRole(Ac00501VO requestParams) {
        QAcRole qAcRole = QAcRole.acRole;

        Predicate predicate = qAcRole.roleUuid.eq(requestParams.getRoleUuid());

        return acRoleRepository.findOne(predicate);
    }

    /**********************************************************************************************
     * 롤별 퍼미션 관련  Service Methods
     **********************************************************************************************
     */

    /**
     * 롤 별 퍼미션 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00502VO> findRolePermission(Pageable pageable, RequestParams<Ac00502VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(ac005Mapper.findRolePermission(requestParams.getString("roleUuid")), pageable, filter, Ac00502VO.class);
    }

    /**
     * 그룹 사용자 정보 저장
     *
     * @param ac00502VOList the ac 00502 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveRolePermission(List<Ac00502VO> ac00502VOList) {
        for (Ac00502VO ac00502VO : ac00502VOList) {
            AcRolePermission acRolePermission = ModelMapperUtils.map(ac00502VO, AcRolePermission.class);
            AcRolePermission orgRolePermission = findOneRolePermission(ac00502VO);

            if (orgRolePermission == null) {
                // created
                acRolePermission.setRolePermissionUuid(UUIDUtils.getUUID());
                acRolePermissionRepository.save(acRolePermission);
            } else {
                if (ac00502VO.isDeleted()) {
                    acRolePermissionRepository.delete(acRolePermission);
                } else {
                    acRolePermission.setInsertDate(orgRolePermission.getInsertDate());
                    acRolePermission.setInsertUuid(orgRolePermission.getInsertUuid());

                    acRolePermissionRepository.save(acRolePermission);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * 그룹 사용자 정보 단건 조회
     *
     * @param requestParams the request params
     * @return ac user group user
     */
    public AcRolePermission findOneRolePermission(Ac00502VO requestParams) {
        QAcRolePermission qAcRolePermission = QAcRolePermission.acRolePermission;

        Predicate predicate = qAcRolePermission.rolePermissionUuid.eq(requestParams.getRolePermissionUuid());

        return acRolePermissionRepository.findOne(predicate);
    }
}