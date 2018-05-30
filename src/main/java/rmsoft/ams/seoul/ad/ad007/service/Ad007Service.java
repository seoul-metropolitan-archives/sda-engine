package rmsoft.ams.seoul.ad.ad007.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.ad.ad007.vo.Ad007;

import javax.inject.Inject;

@Service
public class Ad007Service extends BaseService<Ad007, String> {

    public Page<Ad007> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ad007.class);
    }
}