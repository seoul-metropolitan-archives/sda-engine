package rmsoft.ams.seoul.cl.cl002.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.cl.cl002.dao.Cl002Mapper;
import rmsoft.ams.seoul.cl.cl002.domain.Cl002;

import java.util.List;

@Service
public class Cl002Service extends BaseService<Cl002, Cl002.Cl002Id>
{
    @Autowired
    private Cl002Mapper mapper;

    public List<Cl002> getServiceList() {
        return mapper.getServiceList();
    }
}
