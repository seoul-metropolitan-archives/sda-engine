package rmsoft.ams.seoul.ac.ac012.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ac.ac012.vo.Ac012;

@Service
public class Ac012Service extends BaseService<Ac012, String> {

    public Page<Ac012> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ac012.class);
    }
}