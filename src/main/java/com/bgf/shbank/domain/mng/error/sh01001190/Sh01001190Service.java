package com.bgf.shbank.domain.mng.error.sh01001190;

import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class Sh01001190Service extends BaseService<Sh01001190, Sh01001190.Sh01001190Id> {

    @Inject
    public Sh01001190Service(Sh01001190Repository sh01001190Repository) {
        super(sh01001190Repository);
    }

    public Page<Sh01001190> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh01001190.class);
    }
}