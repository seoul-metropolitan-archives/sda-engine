package com.bgf.shbank.domain.mng.error.sh01001130;

import com.bgf.shbank.utils.ModelMapperUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class Sh01001130Service extends BaseService<Sh01001130, Sh01001130.Sh01001130Id> {

    @Inject
    public Sh01001130Service(Sh01001130Repository sh01001130Repository) {
        super(sh01001130Repository);
    }

    public Page<Sh01001130> find(Pageable pageable, RequestParams<Sh01001130VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh01001130 qSh01001130 = QSh01001130.sh01001130;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001130.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001130.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001130.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh01001130.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh01001130.errorDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh01001130.errorDatetime.desc();
        List<Sh01001130> resultList = select().from(qSh01001130).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh01001130.class);
    }

    public Sh01001130VO findOne(RequestParams<Sh01001130VO> requestParams) {

        Sh01001130.Sh01001130Id id = new Sh01001130.Sh01001130Id();

        id.setErrorDatetime(requestParams.getTimestamp("errorDatetime"));
        id.setBranchCode(requestParams.getString("branchCode"));
        id.setCornerCode(requestParams.getString("cornerCode"));
        id.setTerminalNo(requestParams.getString("terminalNo"));

        return buildVO(findOne(id));
    }

    private Sh01001130VO buildVO(Sh01001130 sh01001130) {

        if (sh01001130 == null) {
            return new Sh01001130VO();
        } else {
            BoundMapperFacade<Sh01001130, Sh01001130VO> mapper =
                    ModelMapperUtils.getMapper("Sh01001130", this.getClass().getPackage().getName());
            return mapper.map(sh01001130);
        }
    }
}