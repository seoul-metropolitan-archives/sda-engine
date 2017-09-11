package com.bgf.shbank.domain.mng.equip.sh05001110;

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
public class Sh05001110Service extends BaseService<Sh05001110, Sh05001110.Sh05001110Id> {

    @Autowired
    private Sh05001110Repository repo;

    @Inject
    public Sh05001110Service(Sh05001110Repository sh05001110Repository) {
        super(sh05001110Repository);
    }

    public Page<Sh05001110> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh05001110.class);
    }


    public Sh05001110VO findOne(RequestParams<Sh05001110VO> requestParams) {

        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        Timestamp overhaulDate = DateUtils.convertToTimestamp(requestParams.getString("overhaulDate"), "yyyy-MM-dd");

        QSh05001110 qSh05001110 = QSh05001110.sh05001110;
        BooleanBuilder builder = new BooleanBuilder();


        builder.and(qSh05001110.branchCode.eq(branchCode))
                .and(qSh05001110.cornerCode.eq(cornerCode))
                .and(qSh05001110.overhaulDate.eq(overhaulDate));
        return buildVO(repo.findOne(builder));
    }


    private Sh05001110VO buildVO(Sh05001110 sh05001110) {

        if (sh05001110 == null) {
            return new Sh05001110VO();
        } else {
            BoundMapperFacade<Sh05001110, Sh05001110VO> mapper =
                    ModelMapperUtils.getMapper("Sh05001110", this.getClass().getPackage().getName());
            return mapper.map(sh05001110);
        }
    }

}