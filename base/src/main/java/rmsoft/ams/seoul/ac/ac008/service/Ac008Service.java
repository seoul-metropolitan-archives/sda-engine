/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac008.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ac.ac008.dao.Ac008Mapper;
import rmsoft.ams.seoul.ac.ac008.vo.Ac00801VO;
import rmsoft.ams.seoul.common.domain.AdMenu;
import rmsoft.ams.seoul.common.domain.AdProgram;
import rmsoft.ams.seoul.common.domain.QAcRoleMenu;
import rmsoft.ams.seoul.common.domain.QAdMenu;
import rmsoft.ams.seoul.common.repository.AcRoleMenuRepository;
import rmsoft.ams.seoul.common.repository.AdMenuRepository;
import rmsoft.ams.seoul.common.repository.AdProgramRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 008 service.
 */
@Slf4j
@Service
public class Ac008Service extends BaseService {

    @Autowired
    private AdProgramRepository adProgramRepository;

    @Autowired
    private AdMenuRepository adMenuRepository;

    @Autowired
    private AcRoleMenuRepository acRoleMenuRepository;

    @Inject
    private Ac008Mapper ac008Mapper;

    /**********************************************************************************************
     * 퍼미션 관련  Service Methods
     **********************************************************************************************
     */

    /**
     * 프로그램 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page page
     */
    public Page<Ac00801VO> findAllProgram(Pageable pageable, RequestParams<Ac00801VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        Ac00801VO ac00801VO = new Ac00801VO();
        ac00801VO.setProgramId(requestParams.getString("programId"));
        ac00801VO.setProgramName(requestParams.getString("programName"));
        ac00801VO.setServiceUuid(requestParams.getString("serviceUuid"));
        ac00801VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac008Mapper.findAllProgram(ac00801VO), pageable, filter, Ac00801VO.class);
    }

    /**
     * 프로그램 정보 저장
     *
     * @param ac00801VOList the ac 00802 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse saveProgram(List<Ac00801VO> ac00801VOList) {
        List<AdProgram> adProgramList = ModelMapperUtils.mapList(ac00801VOList, AdProgram.class);
        AdProgram orgAdProgram = null;

        for (AdProgram adProgram : adProgramList) {

            orgAdProgram = adProgramRepository.findOne(adProgram.getId());

            if (orgAdProgram == null) {
                // created
                adProgram.setProgramId(adProgram.getProgramId().toUpperCase());
                //adProgram.setProgramUuid(UUIDUtils.getUUID());
                adProgramRepository.save(adProgram);
            } else {
                if (adProgram.isDeleted()) {
                    adProgramRepository.delete(adProgram);

                    // 프로그램이 삭제되면 관련 Menu 삭제
                    QAdMenu qAdMenu = QAdMenu.adMenu;
                    Predicate predicate = qAdMenu.programUuid.eq(adProgram.getProgramUuid());

                    Iterable<AdMenu> delMenuList = adMenuRepository.findAll(predicate);

                    delMenuList.forEach(adMenu -> {
                        // 메뉴삭제
                        adMenuRepository.delete(adMenu.getId());

                        // 메뉴가 삭제되므로 관련 Role Menu 도 찾아서 삭제
                        QAcRoleMenu qAcRoleMenu = QAcRoleMenu.acRoleMenu;
                        acRoleMenuRepository.delete(acRoleMenuRepository.findAll(qAcRoleMenu.menuUuid.eq(adMenu.getMenuUuid())));
                    });

                } else {
                    adProgram.setInsertDate(orgAdProgram.getInsertDate());
                    adProgram.setInsertUuid(orgAdProgram.getInsertUuid());

                    adProgramRepository.save(adProgram);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}