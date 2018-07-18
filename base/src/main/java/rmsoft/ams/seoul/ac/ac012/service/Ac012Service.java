package rmsoft.ams.seoul.ac.ac012.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.ac.ac012.vo.Ac012;

import javax.inject.Inject;

@Service
public class Ac012Service extends BaseService<Ac012, String> {

    public Page<Ac012> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ac012.class);
    }
}