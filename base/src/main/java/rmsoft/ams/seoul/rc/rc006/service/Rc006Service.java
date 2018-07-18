package rmsoft.ams.seoul.rc.rc006.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.RcLevelOfDescription;
import rmsoft.ams.seoul.common.repository.RcLevelOfDescriptionRepository;
import rmsoft.ams.seoul.rc.rc006.dao.Rc006Mapper;
import rmsoft.ams.seoul.rc.rc006.vo.Rc00601VO;

import java.util.List;

/**
 * The type Rc 006 service.
 */
@Service
public class Rc006Service extends BaseService {

    @Autowired
    private Rc006Mapper mapper;

    @Autowired
    private RcLevelOfDescriptionRepository rcLevelOfDescriptionRepository;

    /**
     * Search level of description list.
     *
     * @param param the param
     * @return the list
     */
    public List<Rc00601VO> searchLevelOfDescription(Rc00601VO param) {
        return mapper.searchLevelOfDescription(param);
    }

    /**
     * Save level of description api response.
     *
     * @param rc00601VOList the rc 00601 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveLevelOfDescription(List<Rc00601VO> rc00601VOList) {

        List<RcLevelOfDescription> rcLevelOfDescriptionList = ModelMapperUtils.mapList(rc00601VOList, RcLevelOfDescription.class);

        RcLevelOfDescription orgRcLevelOfDescription = null;

        for (RcLevelOfDescription rcLevelOfDescription : rcLevelOfDescriptionList) {
            if (rcLevelOfDescription.isCreated()) {
                rcLevelOfDescriptionRepository.save(rcLevelOfDescription);
            } else if (rcLevelOfDescription.isModified()) {
                orgRcLevelOfDescription = rcLevelOfDescriptionRepository.findOne(rcLevelOfDescription.getId());
                rcLevelOfDescription.setInsertUuid(orgRcLevelOfDescription.getInsertUuid());
                rcLevelOfDescription.setInsertDate(orgRcLevelOfDescription.getInsertDate());
                rcLevelOfDescriptionRepository.save(rcLevelOfDescription);
            } else if (rcLevelOfDescription.isDeleted()) {
                rcLevelOfDescriptionRepository.delete(rcLevelOfDescription);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}
