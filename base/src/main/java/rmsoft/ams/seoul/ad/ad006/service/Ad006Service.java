package rmsoft.ams.seoul.ad.ad006.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ad.ad006.dao.Ad006Mapper;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00601VO;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00602VO;
import rmsoft.ams.seoul.common.domain.AdEntityColumn;
import rmsoft.ams.seoul.common.domain.AdEntityType;
import rmsoft.ams.seoul.common.repository.AdEntityColumnRepository;
import rmsoft.ams.seoul.common.repository.AdEntityTypeRepository;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.List;

/**
 * The type Ad 006 service.
 */
@Service
public class Ad006Service extends BaseService {

    @Autowired
    private Ad006Mapper mapper;

    @Autowired
    private AdEntityTypeRepository adEntityTypeRepository;

    @Autowired
    private AdEntityColumnRepository adEntityColumnRepository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad00601VO> searchEntityType(RequestParams<Ad00601VO> param) {
        Ad00601VO ad00601VO = new Ad00601VO();

        ad00601VO.setEntityType(param.getString("entityType"));
        ad00601VO.setEntityName(param.getString("entityName"));
        ad00601VO.setUseYN(param.getString("useYN"));

        return mapper.searchEntityType(ad00601VO);
    }

    /**
     * Gets entity column list.
     *
     * @param param the param
     * @return the entity column list
     */
    public List<Ad00602VO> getEntityColumnList(RequestParams<Ad00601VO> param) {
        Ad00601VO ad00601VO = new Ad00601VO();

        ad00601VO.setEntityType(param.getString("entityType"));

        return mapper.getEntityColumnList(ad00601VO);
    }

    /**
     * Save entity type api response.
     *
     * @param ad00601VOList the ad 00601 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveEntityType(List<Ad00601VO> ad00601VOList) {

        List<AdEntityType> adEntityTypeList = ModelMapperUtils.mapList(ad00601VOList, AdEntityType.class);

        AdEntityType orgAdEntityType = null;

        for (AdEntityType adEntityType : adEntityTypeList) {
            if (adEntityType.isCreated()) {
                adEntityTypeRepository.save(adEntityType);
            } else if (adEntityType.isModified()) {
                orgAdEntityType = adEntityTypeRepository.findOne(adEntityType.getId());
                adEntityType.setInsertUuid(orgAdEntityType.getInsertUuid());
                adEntityType.setInsertDate(orgAdEntityType.getInsertDate());
                adEntityTypeRepository.save(adEntityType);
            } else if (adEntityType.isDeleted()) {
                if (mapper.checkEntityType(adEntityType.getEntityType()) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AD010_01"));
                } else {
                    adEntityTypeRepository.delete(adEntityType);
                }
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Save entity column api response.
     *
     * @param ad00602VOList the ad 00602 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveEntityColumn(List<Ad00602VO> ad00602VOList) {

        List<AdEntityColumn> adEntityColumnList = ModelMapperUtils.mapList(ad00602VOList, AdEntityColumn.class);

        AdEntityColumn orgAdEntityColumn = null;

        for (AdEntityColumn adEntityColumn : adEntityColumnList) {
            if (adEntityColumn.isModified()) {
                if (adEntityColumn.getEntityColumnUuid() == null) {
                    adEntityColumn.set__created__(true);
                    adEntityColumn.set__modified__(false);
                }
            }

            if (adEntityColumn.isCreated()) {
                adEntityColumn.setEntityColumnUuid(UUIDUtils.getUUID());
                if(null == adEntityColumn.getGlossaryUuid())
                    continue;
                adEntityColumnRepository.save(adEntityColumn);
            } else if (adEntityColumn.isModified()) {
                if(null == adEntityColumn.getGlossaryUuid())
                    continue;

                orgAdEntityColumn = adEntityColumnRepository.findOne(adEntityColumn.getId());
                if(null != orgAdEntityColumn)
                {
                    adEntityColumn.setInsertUuid(orgAdEntityColumn.getInsertUuid());
                    adEntityColumn.setInsertDate(orgAdEntityColumn.getInsertDate());
                }


                adEntityColumnRepository.save(adEntityColumn);
            } else if (adEntityColumn.isDeleted()) {
                if (mapper.checkEntityColumn(adEntityColumn) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AD010_02"));
                } else {
                    adEntityColumnRepository.delete(adEntityColumn);
                }
                //adEntityColumnRepository.delete(adEntityColumn);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}
