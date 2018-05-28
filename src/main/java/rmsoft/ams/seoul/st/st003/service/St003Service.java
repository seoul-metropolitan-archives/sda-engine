package rmsoft.ams.seoul.st.st003.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.st.st003.vo.St003;

import javax.inject.Inject;

@Service
public class St003Service extends BaseService<St003, String> {
    public Page<St003> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, St003.class);
    }
}