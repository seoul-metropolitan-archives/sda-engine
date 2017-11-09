package rmsoft.ams.seoul.ad.ad006.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00601VO;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00602VO;
import rmsoft.ams.seoul.ad.ad006.dao.Ad006Mapper;
import rmsoft.ams.seoul.common.domain.AdEntityColumn;
import rmsoft.ams.seoul.common.domain.AdEntityType;
import rmsoft.ams.seoul.common.repository.AdEntityColumnRepository;
import rmsoft.ams.seoul.common.repository.AdEntityTypeRepository;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.util.List;

@Service
public class Ad006Service extends BaseService {

    @Autowired
    private Ad006Mapper mapper;

    @Autowired
    private AdEntityTypeRepository adEntityTypeRepository;

    @Autowired
    private AdEntityColumnRepository adEntityColumnRepository;

    public List<Ad00601VO> searchEntityType(Ad00601VO param) {
        return mapper.searchEntityType(param);
    }
    public List<Ad00602VO> getEntityColumnList(Ad00601VO param) { return mapper.getEntityColumnList(param); }

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
                if(mapper.checkEntityType(adEntityType.getEntityType())>0){
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("AD010_01"));
                } else {
                    adEntityTypeRepository.delete(adEntityType);
                }
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveEntityColumn(List<Ad00602VO> ad00602VOList) {

        List<AdEntityColumn> adEntityColumnList = ModelMapperUtils.mapList(ad00602VOList, AdEntityColumn.class);

        AdEntityColumn orgAdEntityColumn = null;

        for (AdEntityColumn adEntityColumn : adEntityColumnList) {
            if (adEntityColumn.isCreated()) {
                adEntityColumn.setEntityColumnUuid(UUIDUtils.getUUID());
                adEntityColumnRepository.save(adEntityColumn);
            } else if (adEntityColumn.isModified()) {
                orgAdEntityColumn = adEntityColumnRepository.findOne(adEntityColumn.getId());
                adEntityColumn.setInsertUuid(orgAdEntityColumn.getInsertUuid());
                adEntityColumn.setInsertDate(orgAdEntityColumn.getInsertDate());
                adEntityColumnRepository.save(adEntityColumn);
            } else if (adEntityColumn.isDeleted()) {
                if(mapper.checkEntityColumn(adEntityColumn)>0){
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
