package rmsoft.ams.seoul.st.st002.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.st.st002.vo.St002;

import javax.inject.Inject;

@Service
public class St002Service extends BaseService<St002, String> {
    public Page<St002> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, St002.class);
    }
}