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

/**
 * The type Ac 007 service.
 */
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
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
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
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00702VO> findRoleMenu(Pageable pageable, RequestParams<Ac00702VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        Ac00702VO ac00702VO = new Ac00702VO();
        ac00702VO.setRoleUuid(requestParams.getString("roleUuid"));
        ac00702VO.setProgramId(requestParams.getString("programId"));

        return filter(ac007Mapper.findRoleMenu(ac00702VO), pageable, filter, Ac00702VO.class);
    }

    /**
     * 롤 메뉴 정보 저장
     *
     * @param ac00702VOList the ac 00702 vo list
     * @return api response
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
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00703VO> findPermission(Pageable pageable, RequestParams<Ac00702VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        Ac00702VO ac00702VO = ModelMapperUtils.map(requestParams.getMap(), Ac00702VO.class);
        return filter(ac007Mapper.findPermission(ac00702VO), pageable, filter, Ac00703VO.class);
    }


    /**
     * 퍼미션정보 저장
     *
     * @param ac00703VOList the ac 00703 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse savePermission(List<Ac00703VO> ac00703VOList) {
        List<AcRolePermission> itemList = ModelMapperUtils.mapList(ac00703VOList, AcRolePermission.class);
        AcRolePermission orgItem = null;

        for (AcRolePermission saveItem : itemList) {
            if (saveItem.getRolePermissionUuid() == null) {
                // created
                saveItem.setRolePermissionUuid(UUIDUtils.getUUID());
                acRolePermissionRepository.save(saveItem);
            } else {
                orgItem = acRolePermissionRepository.findOne(saveItem.getId());

                if (saveItem.isDeleted()) {
                    acRolePermissionRepository.delete(saveItem);
                } else {
                    saveItem.setInsertDate(orgItem.getInsertDate());
                    saveItem.setInsertUuid(orgItem.getInsertUuid());

                    acRolePermissionRepository.save(saveItem);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Gets menu list.
     *
     * @param menuList the menu list
     * @return the menu list
     */
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

    /**
     * Gets parent.
     *
     * @param menus the menus
     * @param menu  the menu
     * @return the parent
     */
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