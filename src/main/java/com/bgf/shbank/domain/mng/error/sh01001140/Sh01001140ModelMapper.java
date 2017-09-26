package com.bgf.shbank.domain.mng.error.sh01001140;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by james on 2017-02-09.
 */
@Component
public class Sh01001140ModelMapper extends CustomMapper<Sh01001140, Sh01001140VO> {

    @Override
    public void mapAtoB(Sh01001140 src, Sh01001140VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        LocalDateTime cornerArrivalDatetime = src.getCornerArrivalDatetime().toLocalDateTime();

        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setCornerArrivalDatetime(DateUtils.convertToString(cornerArrivalDatetime, "yyyy-MM-dd HH:mm:ss"));

    }
}

