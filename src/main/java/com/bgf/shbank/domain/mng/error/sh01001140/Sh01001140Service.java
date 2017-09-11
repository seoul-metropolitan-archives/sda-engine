package com.bgf.shbank.domain.mng.error.sh01001140;

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
public class Sh01001140Service extends BaseService<Sh01001140, Sh01001140.Sh01001140Id> {

    @Inject
    public Sh01001140Service(Sh01001140Repository sh01001140Repository) {
        super(sh01001140Repository);
    }

    public Page<Sh01001140> find(Pageable pageable, RequestParams<Sh01001140VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh01001140 qSh01001140 = QSh01001140.sh01001140;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001140.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001140.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001140.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh01001140.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh01001140.errorDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh01001140.errorDatetime.desc();
        List<Sh01001140> resultList = select().from(qSh01001140).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh01001140.class);
    }

    public Sh01001140VO findOne(RequestParams<Sh01001140VO> requestParams) {

        Sh01001140.Sh01001140Id id = new Sh01001140.Sh01001140Id();

        id.setErrorDatetime(requestParams.getTimestamp("errorDatetime"));
        id.setBranchCode(requestParams.getString("branchCode"));
        id.setCornerCode(requestParams.getString("cornerCode"));
        id.setTerminalNo(requestParams.getString("terminalNo"));

        return buildVO(findOne(id));
    }

    private Sh01001140VO buildVO(Sh01001140 sh01001140) {

        if (sh01001140 == null) {
            return new Sh01001140VO();
        } else {
            BoundMapperFacade<Sh01001140, Sh01001140VO> mapper =
                    ModelMapperUtils.getMapper("Sh01001140", this.getClass().getPackage().getName());
            return mapper.map(sh01001140);
        }
    }
}