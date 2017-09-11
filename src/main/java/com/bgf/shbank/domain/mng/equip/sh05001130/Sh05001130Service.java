package com.bgf.shbank.domain.mng.equip.sh05001130;

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
public class Sh05001130Service extends BaseService<Sh05001130, Sh05001130.Sh05001130Id> {

    @Autowired
    private Sh05001130Repository repo;

    @Inject
    public Sh05001130Service(Sh05001130Repository sh05001130Repository) {
        super(sh05001130Repository);
    }

    public Page<Sh05001130> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh05001130.class);
    }

    public Sh05001130VO findOne(RequestParams<Sh05001130VO> requestParams) {

        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        Timestamp overhaulDate = DateUtils.convertToTimestamp(requestParams.getString("overhaulDate"), "yyyy-MM-dd");

        QSh05001130 qSh05001130 = QSh05001130.sh05001130;
        BooleanBuilder builder = new BooleanBuilder();


        builder.and(qSh05001130.branchCode.eq(branchCode))
                .and(qSh05001130.cornerCode.eq(cornerCode))
                .and(qSh05001130.overhaulDate.eq(overhaulDate));
        return buildVO(repo.findOne(builder));
    }


    private Sh05001130VO buildVO(Sh05001130 sh05001130) {

        if (sh05001130 == null) {
            return new Sh05001130VO();
        } else {
            BoundMapperFacade<Sh05001130, Sh05001130VO> mapper =
                    ModelMapperUtils.getMapper("Sh05001130", this.getClass().getPackage().getName());
            return mapper.map(sh05001130);
        }
    }
}