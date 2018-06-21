package rmsoft.ams.seoul.lt.lt004.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.LtPathway;
import rmsoft.ams.seoul.common.repository.LtPathwayRepository;
import rmsoft.ams.seoul.lt.lt004.dao.Lt004Mapper;
import rmsoft.ams.seoul.lt.lt004.vo.Lt00401VO;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.List;

@Service
public class Lt004Service extends BaseService {

    @Autowired
    private Lt004Mapper mapper;

    @Autowired
    private LtPathwayRepository repository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Lt00401VO> searchList(RequestParams<Lt00401VO> param) {
        Lt00401VO lt00401VO = new Lt00401VO();

        lt00401VO.setPathwayName(param.getString("toolName"));
        lt00401VO.setSourceFileFormatUuid(param.getString("softwareUuid"));
        lt00401VO.setTargetFileFormatUuid(param.getString("targetFileFormatUuid"));
        lt00401VO.setToolUuid(param.getString("toolUuid"));
        lt00401VO.setUseYN(param.getString("useYN"));

        return mapper.searchList(lt00401VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  itemList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Lt00401VO> itemList) {

        List<LtPathway> saveList = ModelMapperUtils.mapList(itemList, LtPathway.class);

        LtPathway orgItem = null;

        for (LtPathway saveItem : saveList) {
            if (saveItem.isCreated() || saveItem.isModified()) {
                Lt00401VO lt00401VO = new Lt00401VO();

                lt00401VO.setPathwayName(saveItem.getPathwayName());
                lt00401VO.setSourceFileFormatUuid(saveItem.getSourceFileFormatUuid());
                lt00401VO.setTargetFileFormatUuid(saveItem.getTargetFileFormatUuid());
                lt00401VO.setToolUuid(saveItem.getToolUuid());

                if (saveItem.isCreated()) {
                    // LT_PATHWAY index 무결성 검사 1,2
                    if (mapper.checkIndex01(lt00401VO) > 0 || mapper.checkIndex02(lt00401VO) > 0) {
                        return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AA003"));
                    }
                }
                if (saveItem.isModified()) {
                    orgItem = repository.findOne(saveItem.getId());

                    // LT_PATHWAY index 무결성 검사 1
                    if (!saveItem.getPathwayName().equals(orgItem.getPathwayName())) {
                        if (mapper.checkIndex01(lt00401VO) > 0) {
                            return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AA003"));
                        }
                    }

                    // LT_PATHWAY index 무결성 검사 2
                    if( !saveItem.getSourceFileFormatUuid().equals(orgItem.getSourceFileFormatUuid())
                            || !saveItem.getTargetFileFormatUuid().equals(orgItem.getTargetFileFormatUuid())
                            || !saveItem.getToolUuid().equals(orgItem.getToolUuid()) ){
                        if (mapper.checkIndex02(lt00401VO) > 0) {
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