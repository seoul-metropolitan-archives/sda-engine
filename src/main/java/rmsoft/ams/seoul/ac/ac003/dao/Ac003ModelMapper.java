package rmsoft.ams.seoul.ac.ac003.dao;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;
import rmsoft.ams.seoul.ac.ac003.domain.Ac003;
import rmsoft.ams.seoul.ac.ac003.domain.Ac003VO;

/**
 * Ac003ModelMapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:36
 **/
@Component
public class Ac003ModelMapper extends CustomMapper<Ac003, Ac003VO> {

    @Override
    public void mapAtoB(Ac003 src, Ac003VO dest, MappingContext context) {
        //TODO something
    }
}

