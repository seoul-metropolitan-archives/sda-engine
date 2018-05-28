package rmsoft.ams.seoul.lt.lt001.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.lt.lt001.vo.Lt001;

import javax.inject.Inject;

@Service
public class Lt001Service extends BaseService<Lt001, String> {

    public Page<Lt001> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Lt001.class);
    }
}