package com.bgf.shbank.domain.mng.sla.sh_sla_f0;

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
public class ShSlaF0Service extends BaseService<ShSlaF0, ShSlaF0.ShSlaF0Id> {

    @Inject
    public ShSlaF0Service(ShSlaF0Repository shSlaF0Repository) {
        super(shSlaF0Repository);
    }

    public Page<ShSlaF0> find(Pageable pageable, RequestParams<ShSlaF0VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSlaF0 qShSlaF0 = QShSlaF0.shSlaF0;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSlaF0.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSlaF0.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSlaF0.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSlaF0.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSlaF0.txDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSlaF0.errorDatetime.desc();
        List<ShSlaF0> resultList = select().from(qShSlaF0).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSlaF0.class);
    }

    public void update(List<ShSlaF0> updateList) {
        update(updateList);
    }
}