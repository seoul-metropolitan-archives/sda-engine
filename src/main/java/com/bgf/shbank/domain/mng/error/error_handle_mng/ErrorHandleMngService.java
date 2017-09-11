package com.bgf.shbank.domain.mng.error.error_handle_mng;

import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ErrorHandleMngService extends BaseService<ErrorHandleMng, ErrorHandleMng.ErrorHandleMngId> {

    @Inject
    public ErrorHandleMngService(ErrorHandleMngRepository errorHandleMngRepository) {
        super(errorHandleMngRepository);
    }

    public Page<ErrorHandleMng> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, ErrorHandleMng.class);
    }
}