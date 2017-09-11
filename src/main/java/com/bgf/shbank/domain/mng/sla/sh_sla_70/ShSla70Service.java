package com.bgf.shbank.domain.mng.sla.sh_sla_70;

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
public class ShSla70Service extends BaseService<ShSla70, ShSla70.ShSla70Id> {

    @Inject
    public ShSla70Service(ShSla70Repository shSla70Repository) {
        super(shSla70Repository);
    }

    public Page<ShSla70> find(Pageable pageable, RequestParams<ShSla70VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla70 qShSla70 = QShSla70.shSla70;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSla70.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSla70.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSla70.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSla70.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSla70.txDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla70.errorDatetime.desc();
        List<ShSla70> resultList = select().from(qShSla70).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla70.class);
    }

    public void update(List<ShSla70> updateList) {
        update(updateList);
    }
}