package rmsoft.ams.seoul.ad.ad000.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad000.dao.Ad000Mapper;
import rmsoft.ams.seoul.ad.ad000.vo.Ad00001VO;

import java.util.List;


/**
 * The type Ad 000 service.
 */
@Service
public class Ad000Service extends BaseService {
    @Autowired
    private Ad000Mapper mapper;

    /**
     * Gets service list.
     *
     * @return the service list
     */
    public List<Ad00001VO> getServiceList() {
        return mapper.getServiceList();
    }
}
