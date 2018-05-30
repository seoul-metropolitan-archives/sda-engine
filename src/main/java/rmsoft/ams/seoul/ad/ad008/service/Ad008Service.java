package rmsoft.ams.seoul.ad.ad008.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008;

import javax.inject.Inject;

@Service
public class Ad008Service extends BaseService<Ad008, String> {

    public Page<Ad008> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ad008.class);
    }
}