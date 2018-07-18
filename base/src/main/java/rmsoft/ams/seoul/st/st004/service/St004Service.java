package rmsoft.ams.seoul.st.st004.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.st.st004.vo.St004;

import javax.inject.Inject;

@Service
public class St004Service extends BaseService<St004, String> {
    public Page<St004> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, St004.class);
    }
}