package rmsoft.ams.seoul.ad.ad000.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ad.ad000.dao.Ad000Mapper;
import rmsoft.ams.seoul.ad.ad000.domain.Ad000;

import java.util.List;

@Service
public class Ad000Service extends BaseService<Ad000, Ad000.Ad000Id>
{
    @Autowired
    private Ad000Mapper mapper;

    public List<Ad000> getServiceList() {
        return mapper.getServiceList();
    }
}
