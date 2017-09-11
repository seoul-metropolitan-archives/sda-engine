package com.bgf.shbank.domain.mng.error.sh01001120;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class Sh01001120ModelMapper extends CustomMapper<Sh01001120, Sh01001120VO> {

    @Override
    public void mapAtoB(Sh01001120 src, Sh01001120VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime calleeReqDatetime = src.getCalleeReqDatetime().toLocalDateTime();
        dest.setCalleeReqDatetime(DateUtils.convertToString(calleeReqDatetime, "yyyy-MM-dd HH:mm:ss"));

    }
}

