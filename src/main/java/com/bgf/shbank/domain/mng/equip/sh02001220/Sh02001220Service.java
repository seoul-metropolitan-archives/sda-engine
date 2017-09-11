package com.bgf.shbank.domain.mng.equip.sh02001220;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001220Service extends BaseService<Sh02001220, String> {

    @Inject
    public Sh02001220Service(Sh02001220Repository sh02001220Repository) {
        super(sh02001220Repository);
    }

    public Page<Sh02001220> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001220.class);
    }

    public List<Sh02001220> find(RequestParams<Sh02001220VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String facCode = requestParams.getString("facCode");
        String facGubunCode = requestParams.getString("facGubunCode");

        QSh02001220 qSh02001220 = QSh02001220.sh02001220;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001220.newCornerCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001220.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001220.newCornerCode.eq(cornerCode));
        }

        if (isNotEmpty(facCode)) {
            builder.and(qSh02001220.newFacCode.eq(facCode));
        }

        if (isNotEmpty(facGubunCode)) {
            builder.and(qSh02001220.newFacGubunCode.eq(facGubunCode));
        }

        List<Sh02001220> resultList = select().from(qSh02001220).where(builder).fetch();

        return resultList;
    }
}