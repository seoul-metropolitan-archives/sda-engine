package com.bgf.shbank.domain.mng.etc.sh04001200;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class Sh04001200Service extends BaseService<Sh04001200, Sh04001200.Sh04001200Id> {

    @Inject
    public Sh04001200Service(Sh04001200Repository sh04001200Repository) {
        super(sh04001200Repository);
    }

    public Page<Sh04001200> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001200.class);
    }

    public Page<Sh04001200> find(Pageable pageable, RequestParams<Sh04001200> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh04001200 qSh04001200 = QSh04001200.sh04001200;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001200.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001200.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh04001200.cornerCode.eq(cornerCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh04001200.inoutDatetime.between(startDate, endDate));
        }

        List<Sh04001200> resultList = select().from(qSh04001200).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh04001200.class);
    }

    public List<Sh04001200> find(RequestParams<Sh04001200VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh04001200 qSh04001200 = QSh04001200.sh04001200;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001200.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001200.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh04001200.cornerCode.eq(cornerCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh04001200.inoutDatetime.between(startDate, endDate));
        }

        List<Sh04001200> resultList = select().from(qSh04001200).where(builder).fetch();

        return resultList;
    }
}