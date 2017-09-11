package com.bgf.shbank.domain.mng.error.minwon_mng;

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
public class MinwonMngService extends BaseService<MinwonMng, MinwonMng.MinwonMngId> {

    @Inject
    public MinwonMngService(MinwonMngRepository minwonMngRepository) {
        super(minwonMngRepository);
    }

    public Page<MinwonMng> find(Pageable pageable, RequestParams<MinwonMng> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        String minwonType = requestParams.getString("minwonType");
        String handleDept = requestParams.getString("handleDept");
        String minwonStatus = requestParams.getString("minwonStatus");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QMinwonMng qMinwonMng = QMinwonMng.minwonMng;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qMinwonMng.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qMinwonMng.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qMinwonMng.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qMinwonMng.terminalNo.eq(terminalNo));
        }

        if (isNotEmpty(minwonType)) {
            builder.and(qMinwonMng.minwonType.eq(minwonType));
        }

        if (isNotEmpty(handleDept)) {
            builder.and(qMinwonMng.handleDept.eq(handleDept));
        }

        if (isNotEmpty(minwonStatus)) {
            builder.and(qMinwonMng.minwonStatus.eq(minwonStatus));
        }

        if (startDate != null && endDate != null) {
            builder.and(qMinwonMng.regDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qMinwonMng.regDatetime.desc();
        List<MinwonMng> resultList = select().from(qMinwonMng).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, MinwonMng.class);
    }
}