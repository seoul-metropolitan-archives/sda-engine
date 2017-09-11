package com.bgf.shbank.domain.mng.equip.overhaul_plan;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 30/03/2017.
 */
@Component
public class OverhaulPlanModelMapper extends CustomMapper<OverhaulPlan, OverhaulPlanVO> {

    @Override
    public void mapAtoB(OverhaulPlan src, OverhaulPlanVO dest, MappingContext context) {

        LocalDateTime overhaulDate = src.getOverhaulDate().toLocalDateTime();
        dest.setOverhaulDate(DateUtils.convertToString(overhaulDate, "yyyy-MM-dd"));

    }
}