package com.bgf.shbank.domain.mng.equip.sh02001210;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001210Service extends BaseService<Sh02001210, String> {

    @Inject
    public Sh02001210Service(Sh02001210Repository sh02001210Repository) {
        super(sh02001210Repository);
    }

    public Page<Sh02001210> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001210.class);
    }

    public Page<Sh02001210> find(Pageable pageable, RequestParams<Sh02001210> requestParams) {

        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001210 qSh02001210 = QSh02001210.sh02001210;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001210.newBranchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001210.newCornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001210.newTerminalNo.eq(terminalNo));
        }

        List<Sh02001210> resultList = select().from(qSh02001210).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001210.class);
    }

    public List<Sh02001210> find(RequestParams<Sh02001210VO> requestParams) {

        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001210 qSh02001210 = QSh02001210.sh02001210;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001210.newBranchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001210.newCornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001210.newTerminalNo.eq(terminalNo));
        }

        List<Sh02001210> resultList = select().from(qSh02001210).where(builder).fetch();

        return resultList;
    }
}