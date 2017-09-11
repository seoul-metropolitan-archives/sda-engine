package com.bgf.shbank.domain.mng.etc.sh04001190;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class Sh04001190Service extends BaseService<Sh04001190, Timestamp> {

    @Inject
    public Sh04001190Service(Sh04001190Repository sh04001190Repository) {
        super(sh04001190Repository);
    }

    public Page<Sh04001190> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001190.class);
    }


    public List<Sh04001190> find(RequestParams<Sh04001190VO> requestParams) {

        String filter = requestParams.getString("filter");


        return filter(findAll(), filter);
    }
}