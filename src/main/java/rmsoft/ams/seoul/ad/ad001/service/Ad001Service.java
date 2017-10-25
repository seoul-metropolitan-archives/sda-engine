package rmsoft.ams.seoul.ad.ad001.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad001.dao.Ad001Mapper;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.common.domain.AdConfiguration;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.domain.QAdConfiguration;
import rmsoft.ams.seoul.common.repository.AdConfigurationRepository;

import java.util.List;

@Service("AD001ServiceImpl")
public class Ad001Service extends BaseService {

    @Autowired
    private Ad001Mapper mapper;

    @Autowired
    private AdConfigurationRepository adConfigurationRepository;

    public List<Ad00101VO> getEnviromentList(Ad00101VO param) {
        return mapper.select(param);
    }

    public ApiResponse save(List<Ad00101VO> ad00101VOList) {
        List<AdConfiguration> adConfigurationList = ModelMapperUtils.mapList(ad00101VOList, AdConfiguration.class);
        AdConfiguration orgAdConfiguration = null;

        for (AdConfiguration adConfiguration : adConfigurationList) {
            if (adConfiguration.isCreated()) {
                adConfiguration.setConfigurationUuid(UUIDUtils.getUUID());

                adConfigurationRepository.save(adConfiguration);
            } else if (adConfiguration.isModified()) {
                orgAdConfiguration = findOneConfiguration(adConfiguration);

                adConfiguration.setInsertUuid(orgAdConfiguration.getInsertUuid());
                adConfiguration.setInsertDate(orgAdConfiguration.getInsertDate());

                adConfigurationRepository.save(adConfiguration);

            } else if (adConfiguration.isDeleted()) {
                adConfigurationRepository.delete(adConfiguration);
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
    public AdConfiguration findOneConfiguration(AdConfiguration requestParams) {
        return adConfigurationRepository.findOne(requestParams.getId());
    }
}
