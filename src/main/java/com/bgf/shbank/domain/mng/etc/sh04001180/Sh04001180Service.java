package com.bgf.shbank.domain.mng.etc.sh04001180;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh04001180Service extends BaseService<Sh04001180, Sh04001180.Sh04001180Id> {

    @Inject
    public Sh04001180Service(Sh04001180Repository sh04001180Repository) {
        super(sh04001180Repository);
    }

    public Page<Sh04001180> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001180.class);
    }

    public Page<Sh04001180> find(Pageable pageable, RequestParams<Sh04001180> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QSh04001180 qSh04001180 = QSh04001180.sh04001180;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001180.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001180.branchCode.eq(branchCode));
        }

        List<Sh04001180> resultList = select().from(qSh04001180).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh04001180.class);
    }

    public List<Sh04001180> find(RequestParams<Sh04001180VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QSh04001180 qSh04001180 = QSh04001180.sh04001180;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001180.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001180.branchCode.eq(branchCode));
        }

        List<Sh04001180> resultList = select().from(qSh04001180).where(builder).fetch();

        return resultList;
    }
}