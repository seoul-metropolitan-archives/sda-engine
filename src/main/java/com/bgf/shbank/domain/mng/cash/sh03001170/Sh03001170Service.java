package com.bgf.shbank.domain.mng.cash.sh03001170;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Service
public class Sh03001170Service extends BaseService<Sh03001170, Sh03001170.Sh03001170Id> {

    @Inject
    private Sh03001170Mapper sh03001170Mapper;

    @Inject
    public Sh03001170Service(Sh03001170Repository sh03001170Repository) {
        super(sh03001170Repository);
    }

    public Page<Sh03001170> find(Pageable pageable, RequestParams<Sh03001170> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh03001170 qSh03001170 = QSh03001170.sh03001170;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001170.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001170.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh03001170.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh03001170.cashSendingDate.between(startDate, endDate));
        }

        List<Sh03001170> resultList = select().from(qSh03001170).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh03001170.class);
    }

    public Page<Sh03001170> findAll(Pageable pageable, RequestParams<Sh03001170VO> requestParams) {

        String filter = requestParams.getString("filter");

        Sh03001170 sh03001170 = new Sh03001170();

        sh03001170.setJisaCode(requestParams.getString("jisaCode"));
        sh03001170.setBranchCode(requestParams.getString("branchCode"));
        sh03001170.setTerminalNo(requestParams.getString("terminalNo"));
        sh03001170.setStartDate(requestParams.getTimestamp("startDate"));
        sh03001170.setEndDate(requestParams.getTimestamp("endDate"));

        List<Sh03001170> resultList = sh03001170Mapper.findAll(sh03001170);

        return filter(resultList, pageable, filter, Sh03001170.class);
    }

    public List<Sh03001170> findExcel(RequestParams<Sh03001170VO> requestParams) {

        String filter = requestParams.getString("filter");

        Sh03001170 sh03001170 = new Sh03001170();

        sh03001170.setJisaCode(requestParams.getString("jisaCode"));
        sh03001170.setBranchCode(requestParams.getString("branchCode"));
        sh03001170.setTerminalNo(requestParams.getString("terminalNo"));
        sh03001170.setStartDate(requestParams.getTimestamp("startDate"));
        sh03001170.setEndDate(requestParams.getTimestamp("endDate"));

        List<Sh03001170> resultList = sh03001170Mapper.findAll(sh03001170);

        return filter(resultList, filter);
    }
}