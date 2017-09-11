package com.bgf.shbank.domain.mng.sla.sh_sla_30;

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
public class ShSla30Service extends BaseService<ShSla30, ShSla30.ShSla30Id> {

    @Inject
    public ShSla30Service(ShSla30Repository shSla30Repository) {
        super(shSla30Repository);
    }

    public Page<ShSla30> find(Pageable pageable, RequestParams<ShSla30VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QShSla30 qShSla30 = QShSla30.shSla30;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qShSla30.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qShSla30.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qShSla30.cornerCode.eq(cornerCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qShSla30.txDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qShSla30.minwonDatetime.desc();
        List<ShSla30> resultList = select().from(qShSla30).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, ShSla30.class);
    }

    public void update(List<ShSla30> updateList) {
        update(updateList);
    }
}