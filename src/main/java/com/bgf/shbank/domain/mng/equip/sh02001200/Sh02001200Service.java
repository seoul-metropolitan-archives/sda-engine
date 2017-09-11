package com.bgf.shbank.domain.mng.equip.sh02001200;

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
public class Sh02001200Service extends BaseService<Sh02001200, Timestamp> {

    @Inject
    public Sh02001200Service(Sh02001200Repository sh02001200Repository) {
        super(sh02001200Repository);
    }

    public Page<Sh02001200> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001200.class);
    }

    public Page<Sh02001200> find(Pageable pageable, RequestParams<Sh02001200> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");

        QSh02001200 qSh02001200 = QSh02001200.sh02001200;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001200.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001200.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001200.cornerCode.eq(cornerCode));
        }

        List<Sh02001200> resultList = select().from(qSh02001200).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001200.class);
    }

    public List<Sh02001200> find(RequestParams<Sh02001200VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");

        QSh02001200 qSh02001200 = QSh02001200.sh02001200;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001200.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001200.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001200.cornerCode.eq(cornerCode));
        }

        List<Sh02001200> resultList = select().from(qSh02001200).where(builder).fetch();

        return resultList;
    }
}