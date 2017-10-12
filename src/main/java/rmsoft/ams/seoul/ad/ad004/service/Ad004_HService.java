package rmsoft.ams.seoul.ad.ad004.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad004.dao.Ad004Mapper;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;

import java.util.List;

@Service
public class Ad004_HService extends BaseService{

    @Autowired
    private Ad004Mapper mapper;

    public List<Ad00401VO> searchPopupHeader(Ad00401VO param) {
        return mapper.searchPopupHeader(param);
    }
}
