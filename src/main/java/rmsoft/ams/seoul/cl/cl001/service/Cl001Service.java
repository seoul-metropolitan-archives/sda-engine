package rmsoft.ams.seoul.cl.cl001.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl001.dao.Cl001Mapper;
import rmsoft.ams.seoul.cl.cl001.domain.Cl001;

import java.util.List;

@Service
public class Cl001Service extends BaseService<Cl001, Cl001.Cl001Id>
{
    @Autowired
    private Cl001Mapper mapper;

    public List<Cl001> getServiceList() {
        return mapper.getServiceList();
    }
}
