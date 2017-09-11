package com.bgf.shbank.domain.mng.equip.corner_status;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CornerStatusService extends BaseService<CornerStatus, CornerStatus.CornerStatusId> {

    @Inject
    public CornerStatusService(CornerStatusRepository cornerStatusRepository) {
        super(cornerStatusRepository);
    }

    public Page<CornerStatus> find(Pageable pageable, String filter) {

        return filter(findAll(), pageable, filter, CornerStatus.class);
    }

    public Page<CornerStatus> find(Pageable pageable, RequestParams<CornerStatus> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QCornerStatus qCornerStatus = QCornerStatus.cornerStatus;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qCornerStatus.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qCornerStatus.branchCode.eq(branchCode));
        }

        List<CornerStatus> resultList = select().from(qCornerStatus).where(builder).fetch();

        return filter(resultList, pageable, filter, CornerStatus.class);
    }

    public List<CornerStatus> find(RequestParams<CornerStatusVO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QCornerStatus qCornerStatus = QCornerStatus.cornerStatus;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qCornerStatus.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qCornerStatus.branchCode.eq(branchCode));
        }

        List<CornerStatus> resultList = select().from(qCornerStatus).where(builder).fetch();

        return filter(resultList, filter);
    }
}