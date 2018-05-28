package rmsoft.ams.seoul.rs.rs001.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.rs.rs001.vo.Rs001;

import javax.inject.Inject;

@Service
public class Rs001Service extends BaseService<Rs001, String> {
    public Page<Rs001> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Rs001.class);
    }
}