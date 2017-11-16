/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac005.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.domain.user.role.QRoleMenu;
import io.onsemiro.core.domain.user.role.QRolePermission;
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
import rmsoft.ams.seoul.common.domain.QAcAccessControl;
import rmsoft.ams.seoul.common.repository.AcAccessControlRepository;
import rmsoft.ams.seoul.common.repository.AcRoleMenuRepository;
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

    @Autowired
    private AcAccessControlRepository acAccessControlRepository;

    @Autowired
    private AcRoleMenuRepository acRoleMenuRepository;

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
        List<AcRole> acRoleList = ModelMapperUtils.mapList(ac00501VOList, AcRole.class);
        AcRole orgAcRole = null;

        for (AcRole acRole : acRoleList) {
            orgAcRole = acRoleRepository.findOne(acRole.getId());

            if (orgAcRole == null) {
                // created
                //acRole.setRoleUuid(UUIDUtils.getUUID());
                acRoleRepository.save(acRole);
            } else {
                if (acRole.isDeleted()) {
                    acRoleRepository.delete(acRole);

                    // Role 이 삭제 되었으므로, 관련 Access Control 삭제
                    QAcAccessControl qAcAccessControl = QAcAccessControl.acAccessControl;
                    Predicate predicate = qAcAccessControl.roleUuid.eq(acRole.getRoleUuid());
                    acAccessControlRepository.delete(acAccessControlRepository.findAll(predicate));

                    // Role 이 삭제 되었으므로, 관련 Role Permission 삭제
                    QRolePermission qRolePermission = QRolePermission.rolePermission;
                    Predicate predicate1 = qRolePermission.roleUuid.eq(acRole.getRoleUuid());
                    acRolePermissionRepository.delete(acRolePermissionRepository.findAll(predicate1));

                    // Role 이 삭제 되었으므로, 관련 Role Menu 삭제
                    QRoleMenu qRoleMenu = QRoleMenu.roleMenu;
                    Predicate predicate2 = qRoleMenu.roleUuid.eq(acRole.getRoleUuid());
                    acRoleMenuRepository.delete(acRoleMenuRepository.findAll(predicate2));

                } else {
                    acRole.setInsertDate(orgAcRole.getInsertDate());
                    acRole.setInsertUuid(orgAcRole.getInsertUuid());

                    acRoleRepository.save(acRole);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
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
        List<AcRolePermission> acRolePermissionList = ModelMapperUtils.mapList(ac00502VOList, AcRolePermission.class);
        AcRolePermission orgAcRolePermission = null;

        for (AcRolePermission acRolePermission : acRolePermissionList) {
            orgAcRolePermission = acRolePermissionRepository.findOne(acRolePermission.getId());

            if (orgAcRolePermission == null) {
                // created
                acRolePermission.setRolePermissionUuid(UUIDUtils.getUUID());
                acRolePermissionRepository.save(acRolePermission);
            } else {
                if (acRolePermission.isDeleted()) {
                    acRolePermissionRepository.delete(acRolePermission);
                } else {
                    acRolePermission.setInsertDate(orgAcRolePermission.getInsertDate());
                    acRolePermission.setInsertUuid(orgAcRolePermission.getInsertUuid());

                    acRolePermissionRepository.save(acRolePermission);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}