package com.bgf.shbank.domain.mng.error.sh01001160;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by james on 2017-02-09.
 */
@Component
public class Sh01001160ModelMapper extends CustomMapper<Sh01001160, Sh01001160VO> {

    @Override
    public void mapAtoB(Sh01001160 src, Sh01001160VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime cancelReqDatetime = src.getCancleReqDatetime().toLocalDateTime();
        dest.setCancleReqDatetime(DateUtils.convertToString(cancelReqDatetime, "yyyy-MM-dd HH:mm:ss"));

    }
}

