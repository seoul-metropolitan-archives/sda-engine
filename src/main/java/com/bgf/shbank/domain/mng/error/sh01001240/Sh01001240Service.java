package com.bgf.shbank.domain.mng.error.sh01001240;

import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class Sh01001240Service extends BaseService<Sh01001240, Sh01001240.Sh01001240Id> {

    @Inject
    public Sh01001240Service(Sh01001240Repository sh01001240Repository) {
        super(sh01001240Repository);
    }

    public Page<Sh01001240> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh01001240.class);
    }
}