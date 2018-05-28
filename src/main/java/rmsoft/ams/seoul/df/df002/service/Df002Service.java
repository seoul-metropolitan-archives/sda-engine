package rmsoft.ams.seoul.df.df002.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.df.df002.vo.Df002;

import javax.inject.Inject;

@Service
public class Df002Service extends BaseService<Df002, String> {

    public Page<Df002> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Df002.class);
    }
}