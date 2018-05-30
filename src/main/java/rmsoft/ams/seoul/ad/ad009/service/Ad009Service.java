package rmsoft.ams.seoul.ad.ad009.service;

import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.ad.ad009.vo.Ad009;

import javax.inject.Inject;

@Service
public class Ad009Service extends BaseService<Ad009, String> {

    public Page<Ad009> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Ad009.class);
    }
}