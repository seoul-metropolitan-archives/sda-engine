package com.bgf.shbank.domain.mng.sla.sh_sla_b0;

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
public class ShSlaB0Service extends BaseService<ShSlaB0, ShSlaB0.ShSlaB0Id> {

    @Inject
    public ShSlaB0Service(ShSlaB0Repository shSlaB0Repository) {
        super(shSlaB0Repository);
    }

    public Page<ShSlaB0> find(Pageable pageable, RequestParams<ShSlaB0VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSlaB0 qShSlaB0 = QShSlaB0.shSlaB0;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSlaB0.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSlaB0.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSlaB0.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSlaB0.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSlaB0.overhaulDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSlaB0.overhaulDatetime.desc();
        List<ShSlaB0> resultList = select().from(qShSlaB0).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSlaB0.class);
    }

    public void update(List<ShSlaB0> updateList) {
        update(updateList);
    }
}