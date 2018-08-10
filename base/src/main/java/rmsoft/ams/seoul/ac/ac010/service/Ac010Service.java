package rmsoft.ams.seoul.ac.ac010.service;

import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.ac.ac010.vo.Ac010;

@Service
public class Ac010Service extends BaseService<Ac010, String> {

    public Page<Ac010> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ac010.class);
    }
}