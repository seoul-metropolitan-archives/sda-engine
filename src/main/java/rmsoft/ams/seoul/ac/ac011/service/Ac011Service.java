package rmsoft.ams.seoul.ac.ac011.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.ac.ac011.vo.Ac011;

import javax.inject.Inject;

@Service
public class Ac011Service extends BaseService<Ac011, String> {

    public Page<Ac011> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ac011.class);
    }
}