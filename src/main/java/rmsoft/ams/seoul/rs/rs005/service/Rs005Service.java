package rmsoft.ams.seoul.rs.rs005.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.rs.rs005.vo.Rs005;

import javax.inject.Inject;

@Service
public class Rs005Service extends BaseService<Rs005, String> {
    public Page<Rs005> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Rs005.class);
    }
}