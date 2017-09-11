package com.bgf.shbank.domain.mng.equip.sh02001130;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001130Service extends BaseService<Sh02001130, String> {

    @Inject
    public Sh02001130Service(Sh02001130Repository sh02001130Repository) {
        super(sh02001130Repository);
    }

    public Page<Sh02001130> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001130.class);
    }

    public Page<Sh02001130> find(Pageable pageable, RequestParams<Sh02001130> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001130 qSh02001130 = QSh02001130.sh02001130;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001130.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001130.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001130.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001130.terminalNo.eq(terminalNo));
        }

        List<Sh02001130> resultList = select().from(qSh02001130).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001130.class);
    }

    public List<Sh02001130> find(RequestParams<Sh02001130VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001130 qSh02001130 = QSh02001130.sh02001130;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001130.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001130.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001130.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001130.terminalNo.eq(terminalNo));
        }

        List<Sh02001130> resultList = select().from(qSh02001130).where(builder).fetch();

        return resultList;
    }
}