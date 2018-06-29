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
import rmsoft.ams.seoul.common.domain.AdContextualMeta;
import rmsoft.ams.seoul.common.repository.AdContextualMetaRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.List;

@Service
public class Ad007Service extends BaseService {

    @Autowired
    private Ad007Mapper mapper;

    @Autowired
    private AdContextualMetaRepository repository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad00701VO> searchList(RequestParams<Ad00701VO> param) {
        Ad00701VO ad00701VO = new Ad00701VO();

        ad00701VO.setStatusUuid(param.getString("statusUuid"));
        ad00701VO.setEntityType(param.getString("entityType"));
        ad00701VO.setUseYN(param.getString("useYN"));

        return mapper.searchList(ad00701VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  reqList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Ad00701VO> reqList) {

        List<AdContextualMeta> itemList = ModelMapperUtils.mapList(reqList, AdContextualMeta.class);

        AdContextualMeta orgItem = null;
        int degree = 0;

        for (AdContextualMeta item : itemList) {
            if (item.isCreated() || item.isModified()) {
                if(item.isCreated()){ //disposalFreezeEventUuid 없을때
                    item.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD152","Draft"));
                }

                if(item.isModified()) {
                    orgItem = repository.findOne(item.getId());

                    item.setInsertDate(orgItem.getInsertDate());
                    item.setInsertUuid(orgItem.getInsertUuid());
                }

                repository.save(item);
            } else if (item.isDeleted()) {
                if (mapper.checkDelete(item.getAddContextualMetaUuid()) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AD011_02"));
                } else {
                    repository.delete(item);
                }
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
        List<AdContextualMeta> itemList = ModelMapperUtils.mapList(list,AdContextualMeta.class);
        AdContextualMeta orgItem = null;
        int index = 0;
        String changeStatus = "";
        for (AdContextualMeta item : itemList) {
            changeStatus = list.get(index).getChangeStatus() == "" ?  "Draft" : list.get(index).getChangeStatus();
            orgItem = repository.findOne(item.getId());
            item.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD152",changeStatus));
            item.setInsertDate(item.getInsertDate());
            item.setInsertUuid(item.getInsertUuid());
            repository.save(item);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}