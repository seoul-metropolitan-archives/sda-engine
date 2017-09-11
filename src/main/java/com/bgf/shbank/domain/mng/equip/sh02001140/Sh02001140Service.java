package com.bgf.shbank.domain.mng.equip.sh02001140;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001140Service extends BaseService<Sh02001140, String> {

    @Inject
    public Sh02001140Service(Sh02001140Repository sh02001140Repository) {
        super(sh02001140Repository);
    }

    public Page<Sh02001140> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001140.class);
    }

    public Page<Sh02001140> find(Pageable pageable, RequestParams<Sh02001140> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001140 qSh02001140 = QSh02001140.sh02001140;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001140.nouseBranchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001140.nouseJisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001140.nouseCornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001140.nouseTerminalNo.eq(terminalNo));
        }

        List<Sh02001140> resultList = select().from(qSh02001140).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001140.class);
    }

    public List<Sh02001140> find(RequestParams<Sh02001140VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001140 qSh02001140 = QSh02001140.sh02001140;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001140.nouseBranchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001140.nouseJisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001140.nouseCornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001140.nouseTerminalNo.eq(terminalNo));
        }

        List<Sh02001140> resultList = select().from(qSh02001140).where(builder).fetch();

        return resultList;
    }
}