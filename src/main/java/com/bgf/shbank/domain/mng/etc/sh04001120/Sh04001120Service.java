package com.bgf.shbank.domain.mng.etc.sh04001120;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class Sh04001120Service extends BaseService<Sh04001120, Timestamp> {

    @Inject
    public Sh04001120Service(Sh04001120Repository sh04001120Repository) {
        super(sh04001120Repository);
    }

    public Page<Sh04001120> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001120.class);
    }

    public List<Sh04001120> find(RequestParams<Sh04001120VO> requestParams) {

        String filter = requestParams.getString("filter");

        return filter(findAll(), filter);
    }
}