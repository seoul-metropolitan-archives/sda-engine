package com.bgf.shbank.domain.mng.cash.sh03001210;

import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class Sh03001210Service extends BaseService<Sh03001210, Sh03001210.Sh03001210Id> {

    @Inject
    public Sh03001210Service(Sh03001210Repository sh03001210Repository) {
        super(sh03001210Repository);
    }

    public Page<Sh03001210> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh03001210.class);
    }
}