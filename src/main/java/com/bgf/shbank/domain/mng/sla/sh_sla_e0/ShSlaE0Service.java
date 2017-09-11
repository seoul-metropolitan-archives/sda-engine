package com.bgf.shbank.domain.mng.sla.sh_sla_e0;

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
public class ShSlaE0Service extends BaseService<ShSlaE0, ShSlaE0.ShSlaE0Id> {

    @Inject
    public ShSlaE0Service(ShSlaE0Repository shSlaE0Repository) {
        super(shSlaE0Repository);
    }

    public Page<ShSlaE0> find(Pageable pageable, RequestParams<ShSlaE0VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSlaE0 qShSlaE0 = QShSlaE0.shSlaE0;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSlaE0.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSlaE0.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSlaE0.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qShSlaE0.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSlaE0.stndDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSlaE0.stndDate.desc();
        List<ShSlaE0> resultList = select().from(qShSlaE0).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSlaE0.class);
    }

    public void update(List<ShSlaE0> updateList) {
        update(updateList);
    }
}