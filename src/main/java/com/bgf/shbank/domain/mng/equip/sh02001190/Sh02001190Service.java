package com.bgf.shbank.domain.mng.equip.sh02001190;

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
public class Sh02001190Service extends BaseService<Sh02001190, Timestamp> {

    @Inject
    public Sh02001190Service(Sh02001190Repository sh02001190Repository) {
        super(sh02001190Repository);
    }

    public Page<Sh02001190> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001190.class);
    }
    public Page<Sh02001190> find(Pageable pageable, RequestParams<Sh02001190> requestParams) {

        String facCode = requestParams.getString("facCode");
        String facGubunCode = requestParams.getString("facGubunCode");

        QSh02001190 qSh02001190 = QSh02001190.sh02001190;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(facCode)) {
            builder.and(qSh02001190.changeAfterFacCode.eq(facCode));
        }

        if (isNotEmpty(facGubunCode)) {
            builder.and(qSh02001190.changeAfterFacGubunCode.eq(facGubunCode));
        }

        List<Sh02001190> resultList = select().from(qSh02001190).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001190.class);
    }
}