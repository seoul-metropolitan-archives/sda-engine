package rmsoft.ams.seoul.lt.lt003.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.LtTool;
import rmsoft.ams.seoul.common.repository.LtToolRepository;
import rmsoft.ams.seoul.lt.lt003.dao.Lt003Mapper;
import rmsoft.ams.seoul.lt.lt003.vo.Lt00301VO;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.List;

@Service
public class Lt003Service extends BaseService {

    @Autowired
    private Lt003Mapper mapper;

    @Autowired
    private LtToolRepository repository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Lt00301VO> searchList(RequestParams<Lt00301VO> param) {
        Lt00301VO lt00301VO = new Lt00301VO();

        lt00301VO.setToolName(param.getString("toolName"));
        lt00301VO.setSoftwareUuid(param.getString("softwareUuid"));
        lt00301VO.setUseYN(param.getString("useYN"));

        return mapper.searchList(lt00301VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  itemList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Lt00301VO> itemList) {

        List<LtTool> saveList = ModelMapperUtils.mapList(itemList, LtTool.class);

        LtTool orgItem = null;

        for (LtTool saveItem : saveList) {
            if (saveItem.isCreated() || saveItem.isModified()) {

                if (saveItem.isCreated() && mapper.checkDelete(saveItem.getToolName()) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AA003"));
                }else if(saveItem.isModified()){
                    orgItem = repository.findOne(saveItem.getId());

                    if( !saveItem.getToolName().equals(orgItem.getToolName())){
                        if (mapper.checkDelete(saveItem.getToolName()) > 0) {
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