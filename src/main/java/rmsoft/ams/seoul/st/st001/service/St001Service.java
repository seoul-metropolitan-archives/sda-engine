package rmsoft.ams.seoul.st.st001.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.st.st001.vo.St001;

import javax.inject.Inject;

@Service
public class St001Service extends BaseService<St001, String> {
    public Page<St001> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, St001.class);
    }
}