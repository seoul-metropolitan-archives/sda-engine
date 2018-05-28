package rmsoft.ams.seoul.rs.rs002.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.rs.rs002.vo.Rs002;

import javax.inject.Inject;

@Service
public class Rs002Service extends BaseService<Rs002, String> {
    public Page<Rs002> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Rs002.class);
    }
}