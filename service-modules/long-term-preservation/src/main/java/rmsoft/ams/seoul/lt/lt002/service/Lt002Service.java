package rmsoft.ams.seoul.lt.lt002.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.LtSoftware;
import rmsoft.ams.seoul.common.repository.LtSoftwareRepository;
import rmsoft.ams.seoul.lt.lt002.dao.Lt002Mapper;
import rmsoft.ams.seoul.lt.lt002.vo.Lt00201VO;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.List;

@Service
public class Lt002Service extends BaseService {

    @Autowired
    private Lt002Mapper mapper;

    @Autowired
    private LtSoftwareRepository repository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Lt00201VO> searchList(RequestParams<Lt00201VO> param) {
        Lt00201VO lt00201VO = new Lt00201VO();

        lt00201VO.setSoftwareName(param.getString("softwareName"));
        lt00201VO.setUseYN(param.getString("useYN"));

        return mapper.searchList(lt00201VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  itemList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Lt00201VO> itemList) {

        List<LtSoftware> saveList = ModelMapperUtils.mapList(itemList, LtSoftware.class);

        LtSoftware orgItem = null;

        for (LtSoftware saveItem : saveList) {
            if (saveItem.isCreated() || saveItem.isModified()) {
                Lt00201VO paramVO = new Lt00201VO();
                paramVO.setSoftwareName(saveItem.getSoftwareName());
                paramVO.setSoftwareVersion(saveItem.getSoftwareVersion());

                if (saveItem.isCreated() && mapper.checkDelete(paramVO) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AA003"));
                }else if(saveItem.isModified()){
                    orgItem = repository.findOne(saveItem.getId());

                    if( !saveItem.getSoftwareName().equals(orgItem.getSoftwareName()) ||
                            (saveItem.getSoftwareVersion() != null && !saveItem.getSoftwareVersion().equals(orgItem.getSoftwareVersion()))){
                        if (mapper.checkDelete(paramVO) > 0) {
                            return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AA003"));
                        }
                    }

                    saveItem.setInsertDate(orgItem.getInsertDate());
                    saveItem.setInsertUuid(orgItem.getInsertUuid());
                }

                repository.save(saveItem);
            } else if (saveItem.isDeleted()) {
                repository.delete(saveItem);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}