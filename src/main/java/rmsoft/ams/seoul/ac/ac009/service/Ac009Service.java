/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac009.service;

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
import rmsoft.ams.seoul.ac.ac009.dao.Ac009Mapper;
import rmsoft.ams.seoul.ac.ac009.vo.Ac00901VO;
import rmsoft.ams.seoul.common.domain.AdMenu;
import rmsoft.ams.seoul.common.repository.AdMenuRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 009 service.
 */
@Slf4j
@Service
public class Ac009Service extends BaseService {

    @Autowired
    private AdMenuRepository adMenuRepository;

    @Inject
    private Ac009Mapper ac009Mapper;

    /**********************************************************************************************
     * 퍼미션 관련  Service Methods
     **********************************************************************************************
     */

    /**
     * 메뉴 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00901VO> findAllMenu(Pageable pageable, RequestParams<Ac00901VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        Ac00901VO ac00901VO = new Ac00901VO();
        ac00901VO.setParentMenuCode(requestParams.getString("parentMenuCode"));
        ac00901VO.setMenuCode(requestParams.getString("menuCode"));
        ac00901VO.setMenuName(requestParams.getString("menuName"));
        ac00901VO.setProgramId(requestParams.getString("programId"));
        ac00901VO.setProgramName(requestParams.getString("programName"));
        ac00901VO.setParameter(requestParams.getString("parameter"));
        ac00901VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac009Mapper.findAllMenu(ac00901VO), pageable, filter, Ac00901VO.class);
    }

    /**
     * 메뉴 정보 저장
     *
     * @param ac00901VOList the ac 00902 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveMenu(List<Ac00901VO> ac00901VOList) {
        List<AdMenu> adMenuList = ModelMapperUtils.mapList(ac00901VOList, AdMenu.class);
        AdMenu orgAdMenu = null;

        for (AdMenu adMenu : adMenuList) {
            orgAdMenu = adMenuRepository.findOne(adMenu.getId());

            if (orgAdMenu == null) {
                // created
                adMenu.setMenuUuid(UUIDUtils.getUUID());
                adMenuRepository.save(adMenu);
            } else {
                if (adMenu.isDeleted()) {
                    adMenuRepository.delete(adMenu);
                } else {
                    adMenu.setInsertDate(orgAdMenu.getInsertDate());
                    adMenu.setInsertUuid(orgAdMenu.getInsertUuid());

                    adMenuRepository.save(adMenu);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}