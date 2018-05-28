package rmsoft.ams.seoul.lt.lt004.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.lt.lt004.vo.Lt004;

import javax.inject.Inject;

@Service
public class Lt004Service extends BaseService<Lt004, String> {
    public Page<Lt004> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Lt004.class);
    }
}