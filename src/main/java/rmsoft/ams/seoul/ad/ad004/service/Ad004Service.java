package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
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
        for (AdPopupHeader data : list) {
            if (data.isCreated()) {
                popupHeaderRepository.save(data);
            } else if (data.isModified()) {
                popupHeaderRepository.save(data);
            } else if (data.isDeleted()) {
                popupHeaderRepository.delete(data);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public boolean insertPopupSQL(Ad00401VO data) {
        mapper.insertPopupSQL(data);
        return true;
    }

    public List<Ad00402VO> getPopupDetail(Ad00402VO param) {
        return mapper.getPopupDetail(param);
    }

    public ApiResponse savePopupDetail(List<AdPopupDetail> list) {
        for (AdPopupDetail data : list) {
            if (data.isCreated()) {
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
