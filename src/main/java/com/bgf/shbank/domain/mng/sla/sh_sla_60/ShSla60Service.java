package com.bgf.shbank.domain.mng.sla.sh_sla_60;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ShSla60Service extends BaseService<ShSla60, ShSla60.ShSla60Id> {

    @Inject
    public ShSla60Service(ShSla60Repository shSla60Repository) {
        super(shSla60Repository);
    }

    public Page<ShSla60> find(Pageable pageable, RequestParams<ShSla60VO> requestParams) {
        String filter = requestParams.getString("filter");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla60 qShSla60 = QShSla60.shSla60;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate != null) {
            builder.and(qShSla60.regDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla60.regDatetime.desc();
        List<ShSla60> resultList = select().from(qShSla60).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla60.class);
    }

    public void update(List<ShSla60> updateList) {
        update(updateList);
    }
}