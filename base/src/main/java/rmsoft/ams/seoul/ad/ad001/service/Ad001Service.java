package rmsoft.ams.seoul.ad.ad001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad001.dao.Ad001Mapper;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;
import rmsoft.ams.seoul.common.domain.AdConfiguration;
import rmsoft.ams.seoul.common.repository.AdConfigurationRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Ad 001 service.
 */
@Service("AD001ServiceImpl")
public class Ad001Service extends BaseService {

    @Autowired
    private Ad001Mapper mapper;

    @Autowired
    private AdConfigurationRepository adConfigurationRepository;

    /**
     * Gets enviroment list.
     *
     * @param param the param
     * @return the enviroment list
     */
    public List<Ad00101VO> getEnviromentList(Ad00101VO param) {
        return mapper.select(param);
    }

    /**
     * Save api response.
     *
     * @param ad00101VOList the ad 00101 vo list
     * @return the api response
     */
    public ApiResponse save(List<Ad00101VO> ad00101VOList) {
        List<AdConfiguration> adConfigurationList = ModelMapperUtils.mapList(ad00101VOList, AdConfiguration.class);
        AdConfiguration orgAdConfiguration = null;

        for (AdConfiguration adConfiguration : adConfigurationList) {
            if (adConfiguration.isCreated()) {
                //adConfiguration.setConfigurationUuid(UUIDUtils.getUUID());
                adConfiguration.setInsertUuid(SessionUtils.getCurrentLoginUserUuid());
                adConfiguration.setInsertDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
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
     * @param requestParams the request params
     * @return ad configuration
     */
    public AdConfiguration findOneConfiguration(AdConfiguration requestParams) {
        return adConfigurationRepository.findOne(requestParams.getId());
    }
}
