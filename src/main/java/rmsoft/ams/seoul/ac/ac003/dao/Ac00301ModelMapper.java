package rmsoft.ams.seoul.ac.ac003.dao;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.common.domain.AcUser;

/**
 * Ac003ModelMapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -09-26 오후 3:36
 */
@Component
public class Ac00301ModelMapper extends CustomMapper<AcUser, Ac00301VO> {

    @Override
    public void mapAtoB(AcUser src, Ac00301VO dest, MappingContext context) {
        //TODO something
    }
}

