package com.bgf.shbank.domain.mng.etc.sh04001150;

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
public class Sh04001150Service extends BaseService<Sh04001150, Sh04001150.Sh04001150Id> {

    @Inject
    public Sh04001150Service(Sh04001150Repository sh04001150Repository) {
        super(sh04001150Repository);
    }

    public Page<Sh04001150> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001150.class);
    }

    public Page<Sh04001150> find(Pageable pageable, RequestParams<Sh04001150> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh04001150 qSh04001150 = QSh04001150.sh04001150;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001150.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001150.branchCode.eq(branchCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh04001150.errorDatetime.between(startDate, endDate));
        }

        List<Sh04001150> resultList = select().from(qSh04001150).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh04001150.class);
    }

    public List<Sh04001150> find(RequestParams<Sh04001150VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh04001150 qSh04001150 = QSh04001150.sh04001150;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001150.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001150.branchCode.eq(branchCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh04001150.errorDatetime.between(startDate, endDate));
        }

        List<Sh04001150> resultList = select().from(qSh04001150).where(builder).fetch();

        return resultList;
    }
}