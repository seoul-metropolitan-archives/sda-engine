package com.bgf.shbank.domain.mng.sla.sh_sla_a0;

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
public class ShSlaA0Service extends BaseService<ShSlaA0, ShSlaA0.ShSlaA0Id> {

    @Inject
    public ShSlaA0Service(ShSlaA0Repository shSlaA0Repository) {
        super(shSlaA0Repository);
    }

    public Page<ShSlaA0> find(Pageable pageable, RequestParams<ShSlaA0VO> requestParams) {
        String filter = requestParams.getString("filter");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSlaA0 qShSlaA0 = QShSlaA0.shSlaA0;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate != null) {
            builder.and(qShSlaA0.regDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSlaA0.regDatetime.desc();
        List<ShSlaA0> resultList = select().from(qShSlaA0).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSlaA0.class);
    }

    public void update(List<ShSlaA0> updateList) {
        update(updateList);
    }
}