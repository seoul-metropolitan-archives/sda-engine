package com.bgf.shbank.domain.mng.equip.facility_status;

import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class FacilityStatusService extends BaseService<FacilityStatus, FacilityStatus.FacilityStatusId> {

    @Inject
    public FacilityStatusService(FacilityStatusRepository facilityStatusRepository) {
        super(facilityStatusRepository);
    }

    public Page<FacilityStatus> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, FacilityStatus.class);
    }


    public Page<FacilityStatus> find(Pageable pageable, RequestParams<FacilityStatus> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QFacilityStatus qFacilityStatus = QFacilityStatus.facilityStatus;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qFacilityStatus.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qFacilityStatus.branchCode.eq(branchCode));
        }

        List<FacilityStatus> resultList = select().from(qFacilityStatus).where(builder).fetch();

        return filter(resultList, pageable, filter, FacilityStatus.class);
    }

    public List<FacilityStatus> find(RequestParams<FacilityStatusVO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");

        QFacilityStatus qFacilityStatus = QFacilityStatus.facilityStatus;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qFacilityStatus.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qFacilityStatus.branchCode.eq(branchCode));
        }

        List<FacilityStatus> resultList = select().from(qFacilityStatus).where(builder).fetch();

        return filter(resultList, filter);
    }
}