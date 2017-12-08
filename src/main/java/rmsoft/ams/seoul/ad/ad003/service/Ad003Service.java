package rmsoft.ams.seoul.ad.ad003.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ad.ad003.dao.Ad003Mapper;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00301VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00302VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;
import rmsoft.ams.seoul.common.domain.AdCodeDetail;
import rmsoft.ams.seoul.common.domain.AdCodeHeader;
import rmsoft.ams.seoul.common.repository.AdCodeDetailRepository;
import rmsoft.ams.seoul.common.repository.AdCodeHeaderRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Ad 003 service.
 */
@Service
public class Ad003Service extends BaseService {

    @Autowired
    private Ad003Mapper mapper;

    @Autowired
    private AdCodeHeaderRepository adCodeHeaderRepository;

    @Autowired
    private AdCodeDetailRepository adCodeDetailRepository;

    /**
     * Search code header list.
     *
     * @param param the param
     * @return the list
     */
    public List<Ad00301VO> searchCodeHeader(Ad00301VO param) {
        return mapper.searchCodeHeader(param);
    }

    /**
     * Gets code detail list.
     *
     * @param param the param
     * @return the code detail list
     */
    public List<Ad00302VO> getCodeDetailList(Ad00302VO param) {
        return mapper.getCodeDetailList(param);
    }

    /**
     * Save code header api response.
     *
     * @param ad00301VOList the ad 00301 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveCodeHeader(List<Ad00301VO> ad00301VOList) {

        List<AdCodeHeader> adCodeHeaderList = ModelMapperUtils.mapList(ad00301VOList, AdCodeHeader.class);

        AdCodeHeader orgAdCodeHeader = null;
        AdCodeDetail adCodeDetail = null;
        Ad00302VO ad00302VO = null;
        List<AdCodeDetail> adCodeDetailList = null;
        for (AdCodeHeader adCodeHeader : adCodeHeaderList) {
            if (adCodeHeader.isCreated()) {
                //adCodeHeader.setCodeHeaderUuid(UUIDUtils.getUUID());
                adCodeHeaderRepository.save(adCodeHeader);
            } else if (adCodeHeader.isModified()) {
                orgAdCodeHeader = adCodeHeaderRepository.findOne(adCodeHeader.getId());
                adCodeHeader.setInsertUuid(orgAdCodeHeader.getInsertUuid());
                adCodeHeader.setInsertDate(orgAdCodeHeader.getInsertDate());
                adCodeHeaderRepository.save(adCodeHeader);
            } else if (adCodeHeader.isDeleted()) {
                ad00302VO = new Ad00302VO();
                ad00302VO.setCodeHeaderUuid(adCodeHeader.getCodeHeaderUuid());
                adCodeDetailList = ModelMapperUtils.mapList(mapper.getCodeDetailList(ad00302VO),AdCodeDetail.class);

                if(adCodeDetailList.size() > 0)
                    adCodeDetailRepository.delete(adCodeDetailList);

                adCodeHeaderRepository.delete(adCodeHeader);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Save code detail api response.
     *
     * @param ad00302VOList the ad 00302 vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveCodeDetail(List<Ad00302VO> ad00302VOList) {

        List<AdCodeDetail> adCodeHeaderList = ModelMapperUtils.mapList(ad00302VOList, AdCodeDetail.class);

        AdCodeDetail orgAdCodeDetail = null;

        for (AdCodeDetail adCodeDetail : adCodeHeaderList) {
            if (adCodeDetail.isCreated()) {
                //adCodeDetail.setCodeDetailUuid(UUIDUtils.getUUID());
                adCodeDetail.setInsertUuid(SessionUtils.getCurrentLoginUserUuid());
                adCodeDetail.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                adCodeDetail.setInsertDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                adCodeDetail.setUpdateDate(adCodeDetail.getInsertDate());
                adCodeDetailRepository.save(adCodeDetail);
            } else if (adCodeDetail.isModified()) {
                orgAdCodeDetail = adCodeDetailRepository.findOne(adCodeDetail.getId());
                adCodeDetail.setInsertUuid(orgAdCodeDetail.getInsertUuid());
                adCodeDetail.setInsertDate(orgAdCodeDetail.getInsertDate());
                adCodeDetail.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                adCodeDetail.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));

                adCodeDetailRepository.save(adCodeDetail);
            } else if (adCodeDetail.isDeleted()) {
                adCodeDetailRepository.delete(adCodeDetail);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }


    /**
     * Gets code.
     *
     * @param param the param
     * @return the code
     */
    public List<Ad00303VO> getCode(Ad00303VO param) {
        return mapper.getCode(param);
    }
}
