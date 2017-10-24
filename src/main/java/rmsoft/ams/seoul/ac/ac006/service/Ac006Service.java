/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac006.service;

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
import rmsoft.ams.seoul.ac.ac006.dao.Ac006Mapper;
import rmsoft.ams.seoul.ac.ac006.vo.Ac00601VO;
import rmsoft.ams.seoul.common.domain.AcPermission;
import rmsoft.ams.seoul.common.domain.QAcPermission;
import rmsoft.ams.seoul.common.repository.AcPermissionRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Ac 006 service.
 */
@Slf4j
@Service
public class Ac006Service extends BaseService {

    @Autowired
    private AcPermissionRepository acPermissionRepository;

    @Inject
    private Ac006Mapper ac006Mapper;

    /**********************************************************************************************
     * 퍼미션 관련  Service Methods
     **********************************************************************************************
     */

    /**
     * 퍼미션 조회
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return page
     */
    public Page<Ac00601VO> findAllPermission(Pageable pageable, RequestParams<Ac00601VO> requestParams) {
        String filter = requestParams.getString("filter", "");

        Ac00601VO ac00601VO = new Ac00601VO();
        ac00601VO.setPermissionName(requestParams.getString("permissionName"));
        ac00601VO.setPmsProgramUuid(requestParams.getString("pmsProgramUuid"));
        ac00601VO.setPmsEntityTypeUuid(requestParams.getString("pmsEntityTypeUuid"));
        ac00601VO.setPmsFunctionUuid(requestParams.getString("pmsFunctionUuid"));
        ac00601VO.setUseYn(requestParams.getString("useYn"));

        return filter(ac006Mapper.findAllPermission(ac00601VO), pageable, filter, Ac00601VO.class);
    }

    /**
     * 퍼미션 정보 저장
     *
     * @param ac00601VOList the ac 00602 vo list
     * @return api response
     */
    @Transactional
    public ApiResponse savePermission(List<Ac00601VO> ac00601VOList) {
        for (Ac00601VO ac00601VO : ac00601VOList) {
            AcPermission acPermission = ModelMapperUtils.map(ac00601VO, AcPermission.class);
            AcPermission orgPermission = findOnePermission(ac00601VO);

            if (orgPermission == null) {
                // created
                acPermission.setPermissionUuid(UUIDUtils.getUUID());
                acPermissionRepository.save(acPermission);
            } else {
                if (ac00601VO.isDeleted()) {
                    acPermissionRepository.delete(acPermission);
                } else {
                    acPermission.setInsertDate(orgPermission.getInsertDate());
                    acPermission.setInsertUuid(orgPermission.getInsertUuid());

                    acPermissionRepository.save(acPermission);
                }
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * 퍼미션 단건 조회
     *
     * @param requestParams the request params
     * @return ac user group user
     */
    public AcPermission findOnePermission(Ac00601VO requestParams) {
        QAcPermission qAcPermission = QAcPermission.acPermission;

        Predicate predicate = qAcPermission.permissionUuid.eq(requestParams.getPermissionUuid());

        return acPermissionRepository.findOne(predicate);
    }
}