package rmsoft.ams.seoul.ad.ad001.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad001.dao.Ad001Mapper;
import rmsoft.ams.seoul.ad.ad001.vo.Ad00101VO;

@Service("AD001ServiceImpl")
public class Ad001Service extends BaseService{

    @Autowired
    private Ad001Mapper mapper;

    public Iterable<Ad00101VO> getEnviromentList(Ad00101VO param) {
        return mapper.select(param);
    }
}
