package com.bgf.shbank.domain.mng.equip.sh02001170;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001170Service extends BaseService<Sh02001170, String> {

    @Inject
    public Sh02001170Service(Sh02001170Repository sh02001170Repository) {
        super(sh02001170Repository);
    }

    public Page<Sh02001170> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001170.class);
    }

    public Page<Sh02001170> find(Pageable pageable, RequestParams<Sh02001170> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String facCode = requestParams.getString("facCode");
        String facGubunCode = requestParams.getString("facGubunCode");

        QSh02001170 qSh02001170 = QSh02001170.sh02001170;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001170.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001170.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001170.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(facCode)) {
            builder.and(qSh02001170.facCode.eq(facCode));
        }

        if (isNotEmpty(facGubunCode)) {
            builder.and(qSh02001170.facGubunCode.eq(facGubunCode));
        }

        List<Sh02001170> resultList = select().from(qSh02001170).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001170.class);
    }

    public List<Sh02001170> find(RequestParams<Sh02001170VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String facCode = requestParams.getString("facCode");
        String facGubunCode = requestParams.getString("facGubunCode");

        QSh02001170 qSh02001170 = QSh02001170.sh02001170;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001170.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001170.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001170.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(facCode)) {
            builder.and(qSh02001170.facCode.eq(facCode));
        }

        if (isNotEmpty(facGubunCode)) {
            builder.and(qSh02001170.facGubunCode.eq(facGubunCode));
        }

        List<Sh02001170> resultList = select().from(qSh02001170).where(builder).fetch();

        return resultList;
    }
}