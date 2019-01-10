package rmsoft.ams.seoul.ad.ad010.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad010.dao.Ad010Mapper;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01001VO;

import javax.inject.Inject;

@Service
public class Ad010Service extends BaseService {
    @Inject
    private Ad010Mapper ad010Mapper;

    public Ad01001VO getList01() {
        return ad010Mapper.getList01();
    }
}