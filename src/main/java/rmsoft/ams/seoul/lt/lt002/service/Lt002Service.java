package rmsoft.ams.seoul.lt.lt002.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.lt.lt002.vo.Lt002;

import javax.inject.Inject;

@Service
public class Lt002Service extends BaseService<Lt002, String> {
    public Page<Lt002> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Lt002.class);
    }
}