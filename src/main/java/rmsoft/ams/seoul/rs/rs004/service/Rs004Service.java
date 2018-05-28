package rmsoft.ams.seoul.rs.rs004.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.rs.rs004.vo.Rs004;

import javax.inject.Inject;

@Service
public class Rs004Service extends BaseService<Rs004, String> {
    public Page<Rs004> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Rs004.class);
    }
}