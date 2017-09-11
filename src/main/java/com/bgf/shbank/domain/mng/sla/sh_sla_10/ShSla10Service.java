package com.bgf.shbank.domain.mng.sla.sh_sla_10;

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
public class ShSla10Service extends BaseService<ShSla10, ShSla10.ShSla10Id> {

    @Inject
    public ShSla10Service(ShSla10Repository shSla10Repository) {
        super(shSla10Repository);
    }

    public Page<ShSla10> find(Pageable pageable, RequestParams<ShSla10VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla10 qShSla10 = QShSla10.shSla10;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSla10.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSla10.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSla10.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSla10.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSla10.txDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla10.errorDatetime.desc();
        List<ShSla10> resultList = select().from(qShSla10).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla10.class);
    }

    public void update(List<ShSla10> updateList) {
        update(updateList);
    }
}