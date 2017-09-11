package com.bgf.shbank.domain.mng.sla.sh_sla_20;

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
public class ShSla20Service extends BaseService<ShSla20, ShSla20.ShSla20Id> {

    @Inject
    public ShSla20Service(ShSla20Repository shSla20Repository) {
        super(shSla20Repository);
    }

    public Page<ShSla20> find(Pageable pageable, RequestParams<ShSla20VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla20 qShSla20 = QShSla20.shSla20;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSla20.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSla20.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSla20.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSla20.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSla20.txDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla20.errorDatetime.desc();
        List<ShSla20> resultList = select().from(qShSla20).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla20.class);
    }

    public void update(List<ShSla20> updateList) {
        update(updateList);
    }
}