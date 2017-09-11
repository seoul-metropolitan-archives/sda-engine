package com.bgf.shbank.domain.mng.sla.sh_sla_g1;

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
public class ShSlaG1Service extends BaseService<ShSlaG1, ShSlaG1.ShSlaG1Id> {

    @Inject
    public ShSlaG1Service(ShSlaG1Repository shSlaG1Repository) {
        super(shSlaG1Repository);
    }

    public Page<ShSlaG1> find(Pageable pageable, RequestParams<ShSlaG1VO> requestParams) {
        String filter = requestParams.getString("filter");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSlaG1 qShSlaG1 = QShSlaG1.shSlaG1;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate != null) {
            builder.and(qShSlaG1.withdrawDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSlaG1.withdrawDatetime.desc();
        List<ShSlaG1> resultList = select().from(qShSlaG1).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSlaG1.class);
    }

    public void update(List<ShSlaG1> updateList) {
        update(updateList);
    }
}