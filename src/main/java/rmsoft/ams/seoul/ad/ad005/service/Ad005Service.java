package rmsoft.ams.seoul.ad.ad005.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ad.ad005.dao.Ad005Mapper;
import rmsoft.ams.seoul.ad.ad005.vo.Ad00501VO;
import rmsoft.ams.seoul.ad.ad005.vo.Ad00502VO;
import rmsoft.ams.seoul.common.domain.AdGlossary;
import rmsoft.ams.seoul.common.repository.AdGlossaryRepository;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Ad 005 service.
 */
@Service
public class Ad005Service extends BaseService {

    @Autowired
    private Ad005Mapper mapper;

    @Autowired
    private AdGlossaryRepository addGlossaryRepository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad00501VO> searchGlossary(RequestParams<Ad00501VO> param) {
        Ad00501VO ad00501VO = new Ad00501VO();

        ad00501VO.setTermCode(param.getString("termCode"));
        ad00501VO.setTermName(param.getString("termName"));
        ad00501VO.setDescription(param.getString("description"));
        ad00501VO.setUseYN(param.getString("useYN"));

        return mapper.searchGlossary(ad00501VO);
    }

    /**
     * Gets entity column list.
     *
     * @param param the param
     * @return the entity column list
     */
    public List<Ad00502VO> getEntityColumnList(RequestParams<Ad00501VO> param) {
        Ad00502VO ad00502VO = new Ad00502VO();

        if(param.getString("glossaryUuid") == null){
            return new ArrayList<Ad00502VO>();
        }

        ad00502VO.setGlossaryUuid(param.getString("glossaryUuid"));

        return mapper.getEntityColumnList(ad00502VO);
    }

    /**
     * Save entity type api response.
     *
     * @param ad00501VOList the ad 00501 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveGlossary(List<Ad00501VO> ad00501VOList) {

        List<AdGlossary> adGlossaryList = ModelMapperUtils.mapList(ad00501VOList, AdGlossary.class);

        AdGlossary orgAdGlossary = null;

        for (AdGlossary adGlossary : adGlossaryList) {
            if (adGlossary.isCreated()) {
                addGlossaryRepository.save(adGlossary);
            } else if (adGlossary.isModified()) {
                orgAdGlossary = addGlossaryRepository.findOne(adGlossary.getId());
                adGlossary.setInsertUuid(orgAdGlossary.getInsertUuid());
                adGlossary.setInsertDate(orgAdGlossary.getInsertDate());
                addGlossaryRepository.save(adGlossary);
            } else if (adGlossary.isDeleted()) {
                if (mapper.checkGlossary(adGlossary.getGlossaryUuid()) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AD009_01"));
                } else {
                    addGlossaryRepository.delete(adGlossary);
                }
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}
