package rmsoft.ams.seoul.ad.ad007.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ad.ad007.dao.Ad007Mapper;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00701VO;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00702VO;
import rmsoft.ams.seoul.common.domain.AdConMetaSegment;
import rmsoft.ams.seoul.common.domain.AdConMetaSetup;
import rmsoft.ams.seoul.common.repository.AdConMetaSegmentRepository;
import rmsoft.ams.seoul.common.repository.AdConMetaSetupRepository;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class Ad007Service extends BaseService {

    @Autowired
    private Ad007Mapper mapper;

    @Autowired
    private AdConMetaSetupRepository adConMetaSetupRepository;

    @Autowired
    private AdConMetaSegmentRepository adConMetaSegmentRepository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad00701VO> searchSetup(RequestParams<Ad00701VO> param) {
        Ad00701VO ad00701VO = new Ad00701VO();

        ad00701VO.setEntityType(param.getString("entityType"));
        ad00701VO.setSetCode(param.getString("setCode"));
        ad00701VO.setSetName(param.getString("setName"));
        ad00701VO.setUseYN(param.getString("useYN"));
        ad00701VO.setDefaultYN(param.getString("defaultYN"));

        return mapper.searchSetup(ad00701VO);
    }

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad00702VO> searchSegment(RequestParams<Ad00701VO> param) {
        Ad00701VO ad00701VO = new Ad00701VO();
        ad00701VO.setAddMetaTemplateSetUuid(param.getString("addMetaTemplateSetUuid"));
        ad00701VO.setEntityType(param.getString("entityType"));

        return mapper.searchSegment(ad00701VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  reqList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveSetup(List<Ad00701VO> reqList) {

        List<AdConMetaSetup> itemList = ModelMapperUtils.mapList(reqList, AdConMetaSetup.class);

        AdConMetaSetup orgItem = null;
        int degree = 0;

        for (AdConMetaSetup item : itemList) {
            if (item.isCreated() || item.isModified()) {
                if(item.isModified()) {
                    orgItem = adConMetaSetupRepository.findOne(item.getId());

                    item.setInsertDate(orgItem.getInsertDate());
                    item.setInsertUuid(orgItem.getInsertUuid());
                }

                try {
                    adConMetaSetupRepository.save(item);
                }catch(PersistenceException err){
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AD003"));
                }
            } else if (item.isDeleted()) {
                adConMetaSetupRepository.delete(item);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Save entity type api response.
     *
     * @param  reqList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveSegment(List<Ad00702VO> reqList) {

        List<AdConMetaSegment> itemList = ModelMapperUtils.mapList(reqList, AdConMetaSegment.class);

        AdConMetaSegment orgItem = null;
        int degree = 0;

        for (AdConMetaSegment item : itemList) {
            if (item.isCreated() || item.isModified()) {
                if(item.isModified()) {
                    orgItem = adConMetaSegmentRepository.findOne(item.getId());

                    item.setInsertDate(orgItem.getInsertDate());
                    item.setInsertUuid(orgItem.getInsertUuid());
                }

                try {
                    adConMetaSegmentRepository.save(item);
                }catch(PersistenceException err){
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AD003"));
                }
            } else if (item.isDeleted()) {
                adConMetaSegmentRepository.delete(item);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Update status api response.
     *
     * @param list the list
     * @return the api response
     */
    public ApiResponse updateStatus(List<Ad00701VO> list){
        List<AdConMetaSetup> itemList = ModelMapperUtils.mapList(list,AdConMetaSetup.class);
        AdConMetaSetup orgItem = null;
        int index = 0;
        String changeStatus = "";
        for (AdConMetaSetup item : itemList) {
            orgItem = adConMetaSetupRepository.findOne(item.getId());
            item.setInsertDate(orgItem.getInsertDate());
            item.setInsertUuid(orgItem.getInsertUuid());
            adConMetaSetupRepository.save(item);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}