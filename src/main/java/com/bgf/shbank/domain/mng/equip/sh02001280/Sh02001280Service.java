package com.bgf.shbank.domain.mng.equip.sh02001280;

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
public class Sh02001280Service extends BaseService<Sh02001280, String> {

    @Inject
    public Sh02001280Service(Sh02001280Repository sh02001280Repository) {
        super(sh02001280Repository);
    }

    public Page<Sh02001280> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001280.class);
    }

    public Page<Sh02001280> find(Pageable pageable, RequestParams<Sh02001280> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh02001280 qSh02001280 = QSh02001280.sh02001280;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001280.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001280.branchCode.eq(branchCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh02001280.workDate.between(startDate, endDate));
        }

        List<Sh02001280> resultList = select().from(qSh02001280).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh02001280.class);
    }

    public List<Sh02001280> find(RequestParams<Sh02001280VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh02001280 qSh02001280 = QSh02001280.sh02001280;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001280.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001280.branchCode.eq(branchCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh02001280.workDate.between(startDate, endDate));
        }

        List<Sh02001280> resultList = select().from(qSh02001280).where(builder).fetch();

        return resultList;
    }
}