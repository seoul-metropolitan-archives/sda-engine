package com.bgf.shbank.domain.mng.equip.sh05001140;

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
public class Sh05001140Service extends BaseService<Sh05001140, Sh05001140.Sh05001140Id> {

    @Autowired
    private Sh05001140Repository repo;
    
    @Inject
    public Sh05001140Service(Sh05001140Repository sh05001140Repository) {
        super(sh05001140Repository);
    }

    public Page<Sh05001140> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh05001140.class);
    }

    public Sh05001140VO findOne(RequestParams<Sh05001140VO> requestParams) {

        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        Timestamp overhaulDate = DateUtils.convertToTimestamp(requestParams.getString("overhaulDate"), "yyyy-MM-dd");

        QSh05001140 qSh05001140 = QSh05001140.sh05001140;
        BooleanBuilder builder = new BooleanBuilder();


        builder.and(qSh05001140.branchCode.eq(branchCode))
                .and(qSh05001140.cornerCode.eq(cornerCode))
                .and(qSh05001140.overhaulDate.eq(overhaulDate));
        return buildVO(repo.findOne(builder));
    }


    private Sh05001140VO buildVO(Sh05001140 sh05001140) {

        if (sh05001140 == null) {
            return new Sh05001140VO();
        } else {
            BoundMapperFacade<Sh05001140, Sh05001140VO> mapper =
                    ModelMapperUtils.getMapper("Sh05001140", this.getClass().getPackage().getName());
            return mapper.map(sh05001140);
        }
    }
}