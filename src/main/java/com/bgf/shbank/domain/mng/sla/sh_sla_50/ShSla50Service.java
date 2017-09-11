package com.bgf.shbank.domain.mng.sla.sh_sla_50;

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
public class ShSla50Service extends BaseService<ShSla50, ShSla50.ShSla50Id> {

    @Inject
    public ShSla50Service(ShSla50Repository shSla50Repository) {
        super(shSla50Repository);
    }

    public Page<ShSla50> find(Pageable pageable, RequestParams<ShSla50VO> requestParams) {
        String filter = requestParams.getString("filter");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla50 qShSla50 = QShSla50.shSla50;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate != null) {
            builder.and(qShSla50.txDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla50.txDatetime.desc();
        List<ShSla50> resultList = select().from(qShSla50).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla50.class);
    }

    public void update(List<ShSla50> updateList) {
        update(updateList);
    }
}