package com.bgf.shbank.domain.mng.equip.overhaul_plan;

import com.bgf.shbank.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OverhaulPlanService extends BaseService<OverhaulPlan, OverhaulPlan.OverhaulPlanId> {

    @Inject
    public OverhaulPlanService(OverhaulPlanRepository overhaulPlanRepository) {
        super(overhaulPlanRepository);
    }

    public Page<OverhaulPlan> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, OverhaulPlan.class);
    }

    public List<OverhaulPlan> find(RequestParams<OverhaulPlan> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String curMonth = requestParams.getString("curMonth");

        QOverhaulPlan qOverhaulPlan = QOverhaulPlan.overhaulPlan;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qOverhaulPlan.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qOverhaulPlan.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qOverhaulPlan.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(curMonth)) {
            LocalDateTime month = DateUtils.convertToDateTime(curMonth, "yyyy-MM-dd");
            String startMonth = DateUtils.convertToString(month, "yyyy-MM-01");
            String endMonth = DateUtils.convertToString(month.plusMonths(1), "yyyy-MM-01");

            Timestamp startMonthTS = DateUtils.convertToTimestamp(startMonth, "yyyy-MM-dd");
            Timestamp endMonthTS = DateUtils.convertToTimestamp(endMonth, "yyyy-MM-dd");
            builder.and(qOverhaulPlan.overhaulDate.between(startMonthTS, endMonthTS));
        }

        List<OverhaulPlan> resultList = select().from(qOverhaulPlan).where(builder).fetch();

        return resultList;
    }
}