package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.ad.ad004.dao.Ad004Mapper;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;
import rmsoft.ams.seoul.common.domain.AdPopupDetail;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.repository.AdPopupDetailRepository;
import rmsoft.ams.seoul.common.repository.AdPopupHeaderRepository;

import java.util.List;

@Service
public class Ad004Service extends BaseService {

    @Autowired
    private Ad004Mapper mapper;

    @Autowired
    private AdPopupHeaderRepository popupHeaderRepository;

    @Autowired
    private AdPopupDetailRepository popupDetailRepository;

    public List<Ad00401VO> searchPopupHeader(Ad00401VO param) {
        return mapper.searchPopupHeader(param);
    }

    @Transactional
    public ApiResponse savePopupHeader(List<AdPopupHeader> list) {

        Ad00402VO ad00402VO = null;
        List<AdPopupDetail> adPopupDetailList = null;

        for (AdPopupHeader data : list) {
            if (data.isCreated()) {
                if(null == data.getPopupHeaderUuid() || data.getPopupHeaderUuid().equals(""))
                    data.setPopupHeaderUuid(UUIDUtils.getUUID());
                popupHeaderRepository.save(data);
            } else if (data.isModified()) {
                popupHeaderRepository.save(data);
            } else if (data.isDeleted()) {
                ad00402VO = new Ad00402VO();
                ad00402VO.setPopupHeaderUuid(data.getPopupHeaderUuid());
                adPopupDetailList = ModelMapperUtils.mapList(mapper.getPopupDetail(ad00402VO),AdPopupDetail.class);
                if(adPopupDetailList.size() > 0)
                    popupDetailRepository.delete(adPopupDetailList);

                popupHeaderRepository.delete(data);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public List<Ad00402VO> getPopupDetail(Ad00402VO param) {
        return mapper.getPopupDetail(param);
    }

    @Transactional
    public ApiResponse savePopupDetail(List<AdPopupDetail> list) {
        for (AdPopupDetail data : list) {
            if (data.isCreated()) {
                data.setPopupDetailUuid(UUIDUtils.getUUID());
                popupDetailRepository.save(data);
            } else if (data.isModified()) {
                popupDetailRepository.save(data);
            } else if (data.isDeleted()) {
                popupDetailRepository.delete(data);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}
