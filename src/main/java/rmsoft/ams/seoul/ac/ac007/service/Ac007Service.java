/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac007.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
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
import rmsoft.ams.seoul.ac.ac007.dao.Ac007Mapper;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00701VO;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00702VO;
import rmsoft.ams.seoul.ac.ac007.vo.Ac00703VO;
import rmsoft.ams.seoul.common.domain.AcRoleMenu;
import rmsoft.ams.seoul.common.domain.AcRolePermission;
import rmsoft.ams.seoul.common.domain.AcUser;
import rmsoft.ams.seoul.common.repository.AcRoleMenuRepository;
import rmsoft.ams.seoul.common.repository.AcRolePermissionRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Ac007Service extends BaseService {
    @Autowired
    private AcRoleMenuRepository acRoleMenuRepository;

    @Autowired
    private AcRolePermissionRepository acRolePermissionRepository;

    @Inject
    private Ac007Mapper ac007Mapper;

    /**********************************************************************************************
     * ROLE 정보 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 모든 ROLE 조회
     *
     * @param pageable
     * @param requestParams
     * @return
     */
    public Page<Ac00701VO> findAllRole(Pageable pageable, RequestParams<Ac00701VO> requestParams) {

        Ac00701VO ac00701VO = new Ac00701VO();
        ac00701VO.setRoleName(requestParams.getString("roleName"));
        ac00701VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac007Mapper.findAllRole(ac00701VO), pageable, "", Ac00701VO.class);
    }

    /**********************************************************************************************
     * 사용자 그룹 사용자 정보 관련 Service Methods
     **********************************************************************************************
     */

    /**
     * 롤 메뉴 조회
     *
     * @param pageable
     * @param requestParams
     * @return
     */
    public Page<Ac00702VO> findRoleMenu(Pageable pageable, RequestParams<Ac00702VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(ac007Mapper.findRoleMenu(requestParams.getString("roleUuid")), pageable, filter, Ac00702VO.class);
    }

    /**
     * 롤 메뉴 정보 저장
     *
     * @param ac00702VOList
     * @return
     */
    @Transactional
    public ApiResponse saveRoleMenu(List<Ac00702VO> ac00702VOList) {
        List<AcRoleMenu> acRoleMenuList = ModelMapperUtils.mapList(ac00702VOList, AcRoleMenu.class);
        AcRoleMenu orgAcRoleMenu = null;

        for (AcRoleMenu acRoleMenu : acRoleMenuList) {
            if (StringUtils.isNotEmpty(acRoleMenu.getRoleMenuUuid())) {
                orgAcRoleMenu = acRoleMenuRepository.findOne(acRoleMenu.getId());
            }

            if (orgAcRoleMenu == null) {
                // created
                acRoleMenu.setRoleMenuUuid(UUIDUtils.getUUID());
                acRoleMenuRepository.save(acRoleMenu);
            } else {
                if (acRoleMenu.isDeleted()) {
                    acRoleMenuRepository.delete(acRoleMenu);
                } else {
                    acRoleMenu.setInsertDate(orgAcRoleMenu.getInsertDate());
                    acRoleMenu.setInsertUuid(orgAcRoleMenu.getInsertUuid());

                    acRoleMenuRepository.save(acRoleMenu);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**********************************************************************************************
     * 롤 퍼미션 Service Methods
     **********************************************************************************************
     */

    /**
     * 프로그램별 퍼미션 정보 조회
     *
     * @param pageable
     * @param requestParams
     * @return
     */
    public Page<Ac00703VO> findPermission(Pageable pageable, RequestParams<Ac00702VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        return filter(ac007Mapper.findPermission(requestParams.getString("programUuid")), pageable, filter, Ac00703VO.class);
    }


    /**
     * 롤 퍼미션정보 저장
     *
     * @param ac00703VOList
     * @return
     */
    @Transactional
    public ApiResponse saveRolePermission(List<Ac00703VO> ac00703VOList) {
        List<AcRolePermission> acRolePermissionList = ModelMapperUtils.mapList(ac00703VOList, AcRolePermission.class);
        AcRolePermission orgAcAccessControl = null;

        for (AcRolePermission acRolePermission : acRolePermissionList) {
            orgAcAccessControl = acRolePermissionRepository.findOne(acRolePermission.getId());

            if (orgAcAccessControl == null) {
                // created
                acRolePermission.setRolePermissionUuid(UUIDUtils.getUUID());
                acRolePermissionRepository.save(acRolePermission);
            } else {
                if (acRolePermission.isDeleted()) {
                    acRolePermissionRepository.delete(acRolePermission);
                } else {
                    acRolePermission.setInsertDate(orgAcAccessControl.getInsertDate());
                    acRolePermission.setInsertUuid(orgAcAccessControl.getInsertUuid());

                    acRolePermissionRepository.save(acRolePermission);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public List<Ac00702VO> getMenuList(List<Ac00702VO> menuList) {

        List<Ac00702VO> hierarchyList = new ArrayList<>();
        List<Ac00702VO> filterList = new ArrayList<>();

        for (Ac00702VO adMenu : menuList) {
            Ac00702VO parent = getParent(hierarchyList, adMenu);

            if (parent == null) {
                hierarchyList.add(adMenu);
            } else {
                parent.addChildren(adMenu);
            }
        }

        filterList.addAll(hierarchyList);

        return filterList;
    }

    public Ac00702VO getParent(List<Ac00702VO> menus, Ac00702VO menu) {
        Ac00702VO parent = menus.stream().filter(m -> m.getMenuCode().equals(menu.getParentMenuCode())).findAny().orElse(null);

        if (parent == null) {
            for (Ac00702VO _menu : menus) {
                parent = getParent(_menu.getChildren(), menu);

                if (parent != null)
                    break;
            }
        }

        return parent;
    }


    /**
     * VO 생성 Method
     *
     * @param acUser
     * @return
     */
    private Ac00701VO buildVO(AcUser acUser) {

        if (acUser == null) {
            return new Ac00701VO();
        } else {
            BoundMapperFacade<AcUser, Ac00701VO> mapper =
                    ModelMapperUtils.getMapper("AcUser", this.getClass().getPackage().getName());
            return mapper.map(acUser);
        }
    }
}