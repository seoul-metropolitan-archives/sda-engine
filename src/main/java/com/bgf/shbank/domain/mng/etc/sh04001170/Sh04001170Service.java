package com.bgf.shbank.domain.mng.etc.sh04001170;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh04001170Service extends BaseService<Sh04001170, Sh04001170.Sh04001170Id> {

    @Inject
    public Sh04001170Service(Sh04001170Repository sh04001170Repository) {
        super(sh04001170Repository);
    }

    public Page<Sh04001170> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001170.class);
    }

    public Page<Sh04001170> find(Pageable pageable, RequestParams<Sh04001170> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh04001170 qSh04001170 = QSh04001170.sh04001170;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001170.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001170.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh04001170.terminalNo.eq(terminalNo));
        }

        List<Sh04001170> resultList = select().from(qSh04001170).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh04001170.class);
    }

    public List<Sh04001170> find(RequestParams<Sh04001170VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh04001170 qSh04001170 = QSh04001170.sh04001170;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001170.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001170.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh04001170.terminalNo.eq(terminalNo));
        }

        List<Sh04001170> resultList = select().from(qSh04001170).where(builder).fetch();

        return resultList;
    }
}