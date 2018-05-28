package rmsoft.ams.seoul.df.df003.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.df.df003.vo.Df003;

import javax.inject.Inject;

@Service
public class Df003Service extends BaseService<Df003, String> {

    public Page<Df003> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Df003.class);
    }
}