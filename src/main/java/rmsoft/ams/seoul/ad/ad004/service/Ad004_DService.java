package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad004.dao.Ad004Mapper;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;

import java.util.List;

@Service
public class Ad004_DService extends BaseService {

    @Autowired
    private Ad004Mapper mapper;

    public List<Ad00402VO> searchPopupDetail(Ad00402VO param) {
        return mapper.searchPopupDetail(param);
    }
}
