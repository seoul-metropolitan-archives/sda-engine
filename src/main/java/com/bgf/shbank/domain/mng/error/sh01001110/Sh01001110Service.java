package com.bgf.shbank.domain.mng.error.sh01001110;

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
public class Sh01001110Service extends BaseService<Sh01001110, Sh01001110.Sh01001110Id> {

    @Inject
    public Sh01001110Mapper sh01001110Mapper;

    @Inject
    public Sh01001110Service(Sh01001110Repository sh01001110Repository) {
        super(sh01001110Repository);
    }

    public Page<Sh01001110> find(Pageable pageable, RequestParams<Sh01001110VO> requestParams) {
        String filter = requestParams.getString("filter","");

        Sh01001110 sh01001110 = new Sh01001110();
        sh01001110.setJisaCode(requestParams.getString("jisaCode"));
        sh01001110.setBranchCode(requestParams.getString("branchCode"));
        sh01001110.setCornerCode(requestParams.getString("cornerCode"));
        sh01001110.setTerminalNo(requestParams.getString("terminalNo"));
        sh01001110.setStartDate(requestParams.getTimestamp("startDate"));
        sh01001110.setEndDate(requestParams.getTimestamp("endDate"));
        sh01001110.setErrorType(requestParams.getString("errorType"));
        sh01001110.setTxId(requestParams.getString("errorClassifyCode"));

        return filter(sh01001110Mapper.findAll(sh01001110), pageable, filter, Sh01001110.class);
    }

    public Sh01001110VO findOne(RequestParams<Sh01001110VO> requestParams) {

        Sh01001110 sh01001110 = new Sh01001110();

        sh01001110.setErrorDatetime(requestParams.getTimestamp("errorDatetime"));
        sh01001110.setBranchCode(requestParams.getString("branchCode"));
        sh01001110.setCornerCode(requestParams.getString("cornerCode"));
        sh01001110.setTerminalNo(requestParams.getString("terminalNo"));

        return buildVO(sh01001110Mapper.findOne(sh01001110));
    }

    public Sh01001110VO findOneByResentlyErrorDatetime(RequestParams<Sh01001110VO> requestParams) {
        String branchCode = requestParams.getString("branchCode");
        String jisaCode = requestParams.getString("jisaCode");
        String cornerCode = requestParams.getString("cornerCode");
        String ternimalNo = requestParams.getString("terminalNo");

        QSh01001110 qSh01001110 = QSh01001110.sh01001110;
        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001110.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001110.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001110.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(ternimalNo)) {
            builder.and(qSh01001110.terminalNo.eq(ternimalNo));
        }

        builder.and(qSh01001110.stextGubun.eq("1"));

        OrderSpecifier<Timestamp> sortOrder = qSh01001110.errorDatetime.desc();
        List<Sh01001110> resultList = select().from(qSh01001110).where(builder).orderBy(sortOrder).fetch();
        if (resultList.isEmpty()) {
            return null;
        }
        return buildVO(resultList.get(0));
    }


    private Sh01001110VO buildVO(Sh01001110 sh01001110) {

        if (sh01001110 == null) {
            return new Sh01001110VO();
        } else {
            BoundMapperFacade<Sh01001110, Sh01001110VO> mapper =
                    ModelMapperUtils.getMapper("Sh01001110", this.getClass().getPackage().getName());
            return mapper.map(sh01001110);
        }
    }


}