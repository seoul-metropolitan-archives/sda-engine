package rmsoft.ams.seoul.rs.rs003.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.rs.rs003.vo.Rs003;

import javax.inject.Inject;

@Service
public class Rs003Service extends BaseService<Rs003, String> {
    public Page<Rs003> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Rs003.class);
    }
}