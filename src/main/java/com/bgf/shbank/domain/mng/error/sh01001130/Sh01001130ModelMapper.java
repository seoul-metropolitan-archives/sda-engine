package com.bgf.shbank.domain.mng.error.sh01001130;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class Sh01001130ModelMapper extends CustomMapper<Sh01001130, Sh01001130VO> {

    @Override
    public void mapAtoB(Sh01001130 src, Sh01001130VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        LocalDateTime calleePlanDatetime = src.getCalleePlanDatetime().toLocalDateTime();
        LocalDateTime arrivalPlanDatetime = src.getArrivalPlanDatetime().toLocalDateTime();

        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setCalleePlanDatetime(DateUtils.convertToString(calleePlanDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setArrivalPlanDatetime(DateUtils.convertToString(arrivalPlanDatetime, "yyyy-MM-dd HH:mm:ss"));

    }
}

