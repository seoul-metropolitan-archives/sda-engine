package com.bgf.shbank.domain.mng.equip.sh02001270;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001270Service extends BaseService<Sh02001270, String> {

    @Inject
    public Sh02001270Service(Sh02001270Repository sh02001270Repository) {
        super(sh02001270Repository);
    }

    public Page<Sh02001270> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001270.class);
    }

    public Page<Sh02001270> find(Pageable pageable, RequestParams<Sh02001270> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");

        QSh02001270 qSh02001270 = QSh02001270.sh02001270;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001270.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001270.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001270.cornerCode.eq(cornerCode));
        }

        List<Sh02001270> resultList = select().from(qSh02001270).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001270.class);
    }

    public List<Sh02001270> find(RequestParams<Sh02001270VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");

        QSh02001270 qSh02001270 = QSh02001270.sh02001270;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001270.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001270.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001270.cornerCode.eq(cornerCode));
        }

        List<Sh02001270> resultList = select().from(qSh02001270).where(builder).fetch();

        return resultList;
    }
}