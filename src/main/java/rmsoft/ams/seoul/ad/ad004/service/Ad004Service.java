package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad004.dao.Ad004Mapper;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;
import rmsoft.ams.seoul.common.domain.AdPopupDetail;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.repository.AdPopupDetailRepository;
import rmsoft.ams.seoul.common.repository.AdPopupHeaderRepository;

import java.util.List;

@Service
public class Ad004Service extends BaseService{

    @Autowired
    private Ad004Mapper mapper;

    @Autowired
    private AdPopupHeaderRepository  popupHeaderRepository;

    @Autowired
    private AdPopupDetailRepository popupDetailRepository;

    public List<Ad00401VO> searchPopupHeader(Ad00401VO param) {
        return mapper.searchPopupHeader(param);
    }

    public boolean savePopupHeader(List<AdPopupHeader> list)
    {
        for (AdPopupHeader data: list)
        {
            if(data.isCreated() || data.isModified())
            {
                popupHeaderRepository.save(data);
            }
            else if(data.isDeleted())
            {
                popupHeaderRepository.delete(data);
            }
            
        }
        return true;
    }
    public boolean insertPopupSQL(Ad00401VO data) {
        mapper.insertPopupSQL(data);
        return true;
    }

    public List<Ad00402VO> getPopupDetail(Ad00402VO param) {
        return mapper.getPopupDetail(param);
    }

    public boolean savePopupDetail(List<AdPopupDetail> list)
    {
        for (AdPopupDetail data: list)
        {
            if(data.isCreated() || data.isModified())
            {
                popupDetailRepository.save(data);
            }
            else if(data.isDeleted())
            {
                popupDetailRepository.delete(data);
            }

        }
        return true;
    }

}
