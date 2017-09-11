package com.bgf.shbank.domain.mng.equip.sh02001120;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001120Service extends BaseService<Sh02001120, String> {

    @Inject
    public Sh02001120Service(Sh02001120Repository sh02001120Repository) {
        super(sh02001120Repository);
    }

    public Page<Sh02001120> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001120.class);
    }

    public Page<Sh02001120> find(Pageable pageable, RequestParams<Sh02001120> requestParams) {

        String closingBranchNo = requestParams.getString("closingBranchNo");
        String closingCornerCode = requestParams.getString("closingCornerCode");

        QSh02001120 qSh02001120 = QSh02001120.sh02001120;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(closingBranchNo)) {
            builder.and(qSh02001120.closingBranchNo.eq(closingBranchNo));
        }

        if (isNotEmpty(closingCornerCode)) {
            builder.and(qSh02001120.closingCornerCode.eq(closingCornerCode));
        }

        List<Sh02001120> resultList = select().from(qSh02001120).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001120.class);
    }

    public List<Sh02001120> find(RequestParams<Sh02001120VO> requestParams) {

        String closingBranchNo = requestParams.getString("closingBranchNo");
        String closingCornerCode = requestParams.getString("closingCornerCode");

        QSh02001120 qSh02001120 = QSh02001120.sh02001120;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(closingBranchNo)) {
            builder.and(qSh02001120.closingBranchNo.eq(closingBranchNo));
        }

        if (isNotEmpty(closingCornerCode)) {
            builder.and(qSh02001120.closingCornerCode.eq(closingCornerCode));
        }

        List<Sh02001120> resultList = select().from(qSh02001120).where(builder).fetch();

        return filter(resultList, "");
    }
}