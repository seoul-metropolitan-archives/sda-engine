package rmsoft.ams.seoul.ad.ad001.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad001.dao.Ad001Mapper;
import rmsoft.ams.seoul.ad.ad001.domain.Ad001;

@Service("AD001ServiceImpl")
public class Ad001Service extends BaseService<Ad001, Ad001.Ad001Id> {

    @Autowired
    private Ad001Mapper mapper;

    public Iterable<Ad001> getEnviromentList(Ad001 param) {
        return mapper.select(param);
    }
}
