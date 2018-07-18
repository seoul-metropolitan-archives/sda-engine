package rmsoft.ams.seoul.lt.lt001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.LtFileFormat;
import rmsoft.ams.seoul.common.repository.LtFileFormatRepository;
import rmsoft.ams.seoul.lt.lt001.dao.Lt001Mapper;
import rmsoft.ams.seoul.lt.lt001.vo.Lt00101VO;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.List;

@Service
public class Lt001Service extends BaseService {

    @Autowired
    private Lt001Mapper mapper;

    @Autowired
    private LtFileFormatRepository repository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Lt00101VO> searchList(RequestParams<Lt00101VO> param) {
        Lt00101VO lt00101VO = new Lt00101VO();

        lt00101VO.setPuid(param.getString("puid"));
        lt00101VO.setFormatName(param.getString("formatName"));
        lt00101VO.setFormatVersion(param.getString("formatVersion"));
        lt00101VO.setFormatRisk(param.getString("formatRisk"));
        lt00101VO.setExtension(param.getString("extension"));
        lt00101VO.setFormatGroupUuid(param.getString("formatGorup"));
        lt00101VO.setUseYN(param.getString("useYN"));

        return mapper.searchList(lt00101VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  itemList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Lt00101VO> itemList) {

        List<LtFileFormat> saveList = ModelMapperUtils.mapList(itemList, LtFileFormat.class);

        LtFileFormat orgItem = null;

        for (LtFileFormat saveItem : saveList) {
            if (saveItem.isCreated() || saveItem.isModified()) {
                Lt00101VO lt00101VO = new Lt00101VO();

                lt00101VO.setPuid(saveItem.getPuid());
                lt00101VO.setFormatName(saveItem.getFormatName());

                if (saveItem.isCreated() && mapper.checkUpdate(lt00101VO) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AA003"));
                }else if(saveItem.isModified()){
                    orgItem = repository.findOne(saveItem.getId());

                    if( !saveItem.getPuid().equals(orgItem.getPuid()) || !saveItem.getFormatName().equals(orgItem.getFormatName())){
                        if (mapper.checkUpdate(lt00101VO) > 0) {
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