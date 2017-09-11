package com.bgf.shbank.domain.mng.cash.jisa_sije_close;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class JisaSijeCloseService extends BaseService<JisaSijeClose, JisaSijeClose.JisaSijeCloseId> {

    @Inject
    public JisaSijeCloseMapper jisaSijeCloseMapper;

    @Inject
    public JisaSijeCloseService(JisaSijeCloseRepository jisaSijeCloseRepository) {
        super(jisaSijeCloseRepository);
    }

    public Page<JisaSijeClose> find(Pageable pageable, RequestParams<JisaSijeClose> requestParams) {

        Timestamp closeDate = requestParams.getTimestamp("closeDate");
        Timestamp prevCloseDate = new Timestamp(closeDate.getTime() - (1*24*60*60*1000));

        JisaSijeClose jisaSijeClose = new JisaSijeClose();
        jisaSijeClose.setPrevCloseDate(prevCloseDate);
        jisaSijeClose.setCloseDate(closeDate);

        List<JisaSijeClose> resultList = jisaSijeCloseMapper.findAll(jisaSijeClose);

        return filter(resultList, pageable, null, JisaSijeClose.class);
    }
}