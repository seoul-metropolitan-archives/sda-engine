package com.bgf.shbank.domain.mng.equip.sh02001100;

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
public class Sh02001100Service extends BaseService<Sh02001100, String> {

    @Inject
    public Sh02001100Service(Sh02001100Repository sh02001100Repository) {
        super(sh02001100Repository);
    }

    public Page<Sh02001100> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001100.class);
    }

    public Page<Sh02001100> find(Pageable pageable, RequestParams<Sh02001100> requestParams) {

        String filter = requestParams.getString("filter");
        String branchName = requestParams.getString("branchName");
        String cornerName = requestParams.getString("cornerName");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh02001100 qSh02001100 = QSh02001100.sh02001100;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchName)) {
            builder.and(qSh02001100.branchName.eq(branchName));
        }

        if (isNotEmpty(cornerName)) {
            builder.and(qSh02001100.cornerName.eq(cornerName));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh02001100.noticeDatetime.between(startDate, endDate));
        }

        List<Sh02001100> resultList = select().from(qSh02001100).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh02001100.class);
    }

    public List<Sh02001100> find(RequestParams<Sh02001100VO> requestParams) {

        String filter = requestParams.getString("filter");
        String branchName = requestParams.getString("branchName");
        String cornerName = requestParams.getString("cornerName");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh02001100 qSh02001100 = QSh02001100.sh02001100;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchName)) {
            builder.and(qSh02001100.branchName.eq(branchName));
        }

        if (isNotEmpty(cornerName)) {
            builder.and(qSh02001100.cornerName.eq(cornerName));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh02001100.noticeDatetime.between(startDate, endDate));
        }

        List<Sh02001100> resultList = select().from(qSh02001100).where(builder).fetch();

        return filter(resultList, filter);
    }
}