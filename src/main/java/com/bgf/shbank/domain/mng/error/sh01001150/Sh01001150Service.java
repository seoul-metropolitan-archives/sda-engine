package com.bgf.shbank.domain.mng.error.sh01001150;

import com.bgf.shbank.utils.ModelMapperUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class Sh01001150Service extends BaseService<Sh01001150, Sh01001150.Sh01001150Id> {

    @Autowired
    private Sh01001150Repository repo;

    @Inject
    public Sh01001150Service(Sh01001150Repository sh01001150Repository) {
        super(sh01001150Repository);
    }

    public Page<Sh01001150> find(Pageable pageable, RequestParams<Sh01001150VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh01001150 qSh01001150 = QSh01001150.sh01001150;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001150.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001150.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001150.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh01001150.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh01001150.errorDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh01001150.errorDatetime.desc();
        List<Sh01001150> resultList = select().from(qSh01001150).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh01001150.class);
    }

    public Sh01001150VO findOne(RequestParams<Sh01001150VO> requestParams) {
        //LocalDateTime errorDateTime = requestParams.getTimestamp("errorDateTime").toLocalDateTime();

        //String startDate = DateUtils.convertToString(errorDateTime, "yyyy-MM-dd 00:00:00");
        //String endDate = DateUtils.convertToString(errorDateTime, "yyyy-MM-dd 23:59:59");

        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String ternimalNo = requestParams.getString("terminalNo");
        String calleeReqSeqNo = requestParams.getString("calleeReqSeqNo");

        if(StringUtils.isEmpty(calleeReqSeqNo)){
            return buildVO(null);
        }

        QSh01001150 qSh01001150 = QSh01001150.sh01001150;
        BooleanBuilder builder = new BooleanBuilder();


        builder.and(qSh01001150.branchCode.eq(branchCode))
                .and(qSh01001150.cornerCode.eq(cornerCode))
                .and(qSh01001150.terminalNo.eq(ternimalNo))
                .and(qSh01001150.calleeReqSeqNo.eq(calleeReqSeqNo));

        //        .and(qSh01001150.errorDateTime.between(DateUtils.convertToTimestamp(startDate), DateUtils.convertToTimestamp(endDate)));

        return buildVO(repo.findOne(builder));
    }


    private Sh01001150VO buildVO(Sh01001150 sh01001150) {

        if (sh01001150 == null) {
            return new Sh01001150VO();
        } else {
            BoundMapperFacade<Sh01001150, Sh01001150VO> mapper =
                    ModelMapperUtils.getMapper("Sh01001150", this.getClass().getPackage().getName());
            return mapper.map(sh01001150);
        }
    }
}