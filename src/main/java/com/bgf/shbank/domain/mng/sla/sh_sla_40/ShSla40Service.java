package com.bgf.shbank.domain.mng.sla.sh_sla_40;

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
public class ShSla40Service extends BaseService<ShSla40, ShSla40.ShSla40Id> {

    @Inject
    public ShSla40Service(ShSla40Repository shSla40Repository) {
        super(shSla40Repository);
    }

    public Page<ShSla40> find(Pageable pageable, RequestParams<ShSla40VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla40 qShSla40 = QShSla40.shSla40;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSla40.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSla40.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSla40.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSla40.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSla40.txDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla40.errorDatetime.desc();
        List<ShSla40> resultList = select().from(qShSla40).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla40.class);
    }

    public void update(List<ShSla40> updateList) {
        update(updateList);
    }
}