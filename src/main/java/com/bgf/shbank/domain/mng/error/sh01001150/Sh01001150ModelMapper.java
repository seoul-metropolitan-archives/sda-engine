package com.bgf.shbank.domain.mng.error.sh01001150;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by james on 2017-02-09.
 */
@Component
public class Sh01001150ModelMapper extends CustomMapper<Sh01001150, Sh01001150VO> {

    @Override
    public void mapAtoB(Sh01001150 src, Sh01001150VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime handleDateTime = src.getHandleDatetime().toLocalDateTime();
        dest.setHandleDatetime(DateUtils.convertToString(handleDateTime, "yyyy-MM-dd HH:mm:ss"));

    }
}

