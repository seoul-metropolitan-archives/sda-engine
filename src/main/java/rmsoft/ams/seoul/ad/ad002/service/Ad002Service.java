package rmsoft.ams.seoul.ad.ad002.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.ad.ad002.dao.Ad002Mapper;
import rmsoft.ams.seoul.ad.ad002.vo.Ad00201VO;
import rmsoft.ams.seoul.common.domain.AdMessage;
import rmsoft.ams.seoul.common.repository.AdMessageRepository;

import java.util.List;

@Service("Ad002Service")
public class Ad002Service extends BaseService{

    @Autowired
    private Ad002Mapper ad002Mapper;

    @Autowired
    private AdMessageRepository adMessageRepository;

    public List<Ad00201VO> getMessageList(Ad00201VO param) {
        return ad002Mapper.select(param);
    }
    public ApiResponse save(List<Ad00201VO> ad00201VOList)
    {
        List<AdMessage> adMessageList = ModelMapperUtils.mapList(ad00201VOList, AdMessage.class);
        AdMessage orgAdMessage = null;
        for (AdMessage adMessage : adMessageList) {
            if (adMessage.isCreated()) {
                adMessage.setMessageUuid(UUIDUtils.getUUID());
                adMessageRepository.save(adMessage);
            } else if (adMessage.isModified()) {
                orgAdMessage = findOneMessage(adMessage);
                adMessage.setInsertUuid(orgAdMessage.getInsertUuid());
                adMessage.setInsertDate(orgAdMessage.getInsertDate());
                adMessageRepository.save(adMessage);
            } else if (adMessage.isDeleted()) {
                adMessageRepository.delete(adMessage);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    /**
     * 사용자 접근제어 정보 단건 조회
     *
     * @param requestParams
     * @return
     */
    public AdMessage findOneMessage(AdMessage requestParams) {
        return adMessageRepository.findOne(requestParams.getId());
    }
}
