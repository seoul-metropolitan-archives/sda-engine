package rmsoft.ams.seoul.cl.cl003.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl003.dao.Cl003Mapper;
import rmsoft.ams.seoul.cl.cl003.domain.Cl003;

import java.util.List;

@Service
public class Cl003Service extends BaseService<Cl003, Cl003.Cl003Id>
{
    @Autowired
    private Cl003Mapper mapper;

    public List<Cl003> getServiceList() {
        return mapper.getServiceList();
    }
}
