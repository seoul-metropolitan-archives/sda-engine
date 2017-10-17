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

    public boolean insertPopupHeader(List<Ad00401VO> list)
    {
        for (Ad00401VO data: list)
        {
            if(data.isCreated())
            {
                mapper.insertPopupHeader(data);
            }
            else if(data.isModified())
            {
                mapper.updatePopupHeader(data);
            }
            else if(data.isDeleted())
            {
                mapper.deletePopupHeader(data);
            }
            
        }

        //popupHeaderRepository.save(data);
        return true;
    }
    public List<Ad00402VO> searchPopupDetail(Ad00402VO param) {
        return mapper.getPopupDetail(param);
    }
}
