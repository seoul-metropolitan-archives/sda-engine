package com.bgf.shbank.domain.mng.sla.sh_sla_80;

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
public class ShSla80Service extends BaseService<ShSla80, ShSla80.ShSla80Id> {

    @Inject
    public ShSla80Service(ShSla80Repository shSla80Repository) {
        super(shSla80Repository);
    }

    public Page<ShSla80> find(Pageable pageable, RequestParams<ShSla80VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla80 qShSla80 = QShSla80.shSla80;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSla80.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSla80.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSla80.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSla80.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSla80.txDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla80.errorDatetime.desc();
        List<ShSla80> resultList = select().from(qShSla80).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla80.class);
    }

    public void update(List<ShSla80> updateList) {
        update(updateList);
    }
}