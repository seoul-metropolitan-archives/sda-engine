package com.bgf.shbank.domain.mng.equip.sh05001120;

import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;

@Service
public class Sh05001120Service extends BaseService<Sh05001120, Sh05001120.Sh05001120Id> {

    @Autowired
    private Sh05001120Repository repo;

    @Inject
    public Sh05001120Service(Sh05001120Repository sh05001120Repository) {
        super(sh05001120Repository);
    }

    public Page<Sh05001120> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh05001120.class);
    }


    public Sh05001120VO findOne(RequestParams<Sh05001120VO> requestParams) {

        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        Timestamp overhaulDate = DateUtils.convertToTimestamp(requestParams.getString("overhaulDate"), "yyyy-MM-dd");

        QSh05001120 qSh05001120 = QSh05001120.sh05001120;
        BooleanBuilder builder = new BooleanBuilder();


        builder.and(qSh05001120.branchCode.eq(branchCode))
                .and(qSh05001120.cornerCode.eq(cornerCode))
                .and(qSh05001120.overhaulDate.eq(overhaulDate));
        return buildVO(repo.findOne(builder));
    }


    private Sh05001120VO buildVO(Sh05001120 sh05001120) {

        if (sh05001120 == null) {
            return new Sh05001120VO();
        } else {
            BoundMapperFacade<Sh05001120, Sh05001120VO> mapper =
                    ModelMapperUtils.getMapper("Sh05001120", this.getClass().getPackage().getName());
            return mapper.map(sh05001120);
        }
    }
}