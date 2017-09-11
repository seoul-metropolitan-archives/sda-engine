package com.bgf.shbank.domain.mng.equip.sh02001110;

import com.bgf.shbank.utils.ModelMapperUtils;
import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class Sh02001110Service extends BaseService<Sh02001110, Sh02001110.Sh02001110Id> {

    @Inject
    public Sh02001110Service(Sh02001110Repository sh02001110Repository) {
        super(sh02001110Repository);
    }

    public Page<Sh02001110> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001110.class);
    }
    
    public Page<Sh02001110> find(Pageable pageable, RequestParams<Sh02001110> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");

        QSh02001110 qSh02001110 = QSh02001110.sh02001110;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001110.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001110.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001110.cornerCode.eq(cornerCode));
        }

        List<Sh02001110> resultList = select().from(qSh02001110).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001110.class);
    }

    public List<Sh02001110> find(RequestParams<Sh02001110VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");

        QSh02001110 qSh02001110 = QSh02001110.sh02001110;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001110.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001110.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001110.cornerCode.eq(cornerCode));
        }

        List<Sh02001110> resultList = select().from(qSh02001110).where(builder).fetch();

        return resultList;
    }

    public Sh02001110VO findOne(RequestParams<Sh02001110VO> requestParams) {

        Sh02001110.Sh02001110Id id = new Sh02001110.Sh02001110Id();

        id.setJisaCode(requestParams.getString("jisaCode"));
        id.setBranchCode(requestParams.getString("branchCode"));
        id.setCornerCode(requestParams.getString("cornerCode"));
        

        return buildVO(findOne(id));
    }
    
    private Sh02001110VO buildVO(Sh02001110 sh02001110) {

        if (sh02001110 == null) {
            return new Sh02001110VO();
        } else {
            BoundMapperFacade<Sh02001110, Sh02001110VO> mapper =
                    ModelMapperUtils.getMapper("Sh02001110", this.getClass().getPackage().getName());
            return mapper.map(sh02001110);
        }
    }

}