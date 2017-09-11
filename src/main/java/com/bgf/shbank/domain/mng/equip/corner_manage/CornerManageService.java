package com.bgf.shbank.domain.mng.equip.corner_manage;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CornerManageService extends BaseService<CornerManage, CornerManage.CornerManageId> {

    @Inject
    public CornerManageService(CornerManageRepository cornerManageRepository) {
        super(cornerManageRepository);
    }

    public Page<CornerManage> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, CornerManage.class);
    }

    public Page<CornerManage> find(Pageable pageable, RequestParams<CornerManage> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QCornerManage qCornerManage = QCornerManage.cornerManage;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qCornerManage.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qCornerManage.branchCode.eq(branchCode));
        }

        List<CornerManage> resultList = select().from(qCornerManage).where(builder).fetch();

        return filter(resultList, pageable, filter, CornerManage.class);
    }

    public List<CornerManage> find(RequestParams<CornerManageVO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QCornerManage qCornerManage = QCornerManage.cornerManage;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qCornerManage.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qCornerManage.branchCode.eq(branchCode));
        }

        List<CornerManage> resultList = select().from(qCornerManage).where(builder).fetch();

        return resultList;
    }
}