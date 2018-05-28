package rmsoft.ams.seoul.lt.lt003.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.lt.lt003.vo.Lt003;

import javax.inject.Inject;

@Service
public class Lt003Service extends BaseService<Lt003, String> {
    public Page<Lt003> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Lt003.class);
    }
}