package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad004.dao.Ad004Mapper;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.repository.AdPopupHeaderRepository;

import java.util.List;

@Service
public class Ad004Service extends BaseService{

    @Autowired
    private Ad004Mapper mapper;

    @Autowired
    private AdPopupHeaderRepository popupHeaderRepository;

    public List<Ad00401VO> searchPopupHeader(Ad00401VO param) {
        return mapper.searchPopupHeader(param);
    }
    public List<AdPopupHeader> searchPopupHeader(AdPopupHeader header)
    {
        return popupHeaderRepository.findAll();
    }
    public boolean insertPopupHeader(AdPopupHeader data)
    {
        popupHeaderRepository.save(data);
        return true;
    }
    public List<Ad00402VO> searchPopupDetail(Ad00402VO param) {
        return mapper.searchPopupDetail(param);
    }
}
