package rmsoft.ams.seoul.df.df001.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.df.df001.vo.Df001;

@Service
public class Df001Service extends BaseService<Df001, String> {

    public Page<Df001> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Df001.class);
    }
}