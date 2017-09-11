package com.bgf.shbank.domain.mng.equip.sh02001160;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001160Service extends BaseService<Sh02001160, String> {

    @Inject
    public Sh02001160Service(Sh02001160Repository sh02001160Repository) {
        super(sh02001160Repository);
    }

    public Page<Sh02001160> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001160.class);
    }
    
    public Page<Sh02001160> find(Pageable pageable, RequestParams<Sh02001160> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String facCode = requestParams.getString("facCode");
        String facGubunCode = requestParams.getString("facGubunCode");

        QSh02001160 qSh02001160 = QSh02001160.sh02001160;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001160.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001160.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001160.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(facCode)) {
            builder.and(qSh02001160.facCode.eq(facCode));
        }

        if (isNotEmpty(facGubunCode)) {
            builder.and(qSh02001160.facGubunCode.eq(facGubunCode));
        }

        List<Sh02001160> resultList = select().from(qSh02001160).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001160.class);
    }

    public List<Sh02001160> find(RequestParams<Sh02001160VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String facCode = requestParams.getString("facCode");
        String facGubunCode = requestParams.getString("facGubunCode");

        QSh02001160 qSh02001160 = QSh02001160.sh02001160;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001160.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001160.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001160.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(facCode)) {
            builder.and(qSh02001160.facCode.eq(facCode));
        }

        if (isNotEmpty(facGubunCode)) {
            builder.and(qSh02001160.facGubunCode.eq(facGubunCode));
        }

        List<Sh02001160> resultList = select().from(qSh02001160).where(builder).fetch();

        return resultList;
    }
}