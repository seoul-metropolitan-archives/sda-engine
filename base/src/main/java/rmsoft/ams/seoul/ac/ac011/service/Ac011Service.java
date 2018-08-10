package rmsoft.ams.seoul.ac.ac011.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ac.ac011.vo.Ac011;

@Service
public class Ac011Service extends BaseService<Ac011, String> {

    public Page<Ac011> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ac011.class);
    }
}