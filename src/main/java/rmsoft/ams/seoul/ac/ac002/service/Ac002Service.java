package rmsoft.ams.seoul.ac.ac002.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ac.ac002.dao.Ac002Mapper;
import rmsoft.ams.seoul.ac.ac002.vo.Ac002VO;

/**
 * The type Ac 002 service.
 */
@Service
public class Ac002Service extends BaseService
{
    /**
     * The Ac 002 mapper.
     */
    @Autowired
    Ac002Mapper ac002Mapper;

    /**
     * Gets startup program.
     *
     * @param param the param
     * @return the startup program
     */
    public Ac002VO getStartupProgram(Ac002VO param)
    {
        Ac002VO ac002VO = ac002Mapper.getStartupProgram(param);


        return ac002VO;
    }
}
