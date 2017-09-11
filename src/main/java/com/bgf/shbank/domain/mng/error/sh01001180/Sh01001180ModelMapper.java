package com.bgf.shbank.domain.mng.error.sh01001180;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 28/02/2017.
 */
@Component
public class Sh01001180ModelMapper extends CustomMapper<Sh01001180, Sh01001180VO> {

    @Override
    public void mapAtoB(Sh01001180 src, Sh01001180VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setErrorDate(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd"));
        dest.setErrorTime(DateUtils.convertToString(errorDatetime, "HH:mm:ss"));

        if(src.getHandleDatetime() != null) {
            LocalDateTime handleDatetime = src.getHandleDatetime().toLocalDateTime();
            dest.setHandleDatetime(DateUtils.convertToString(handleDatetime, "yyyy-MM-dd HH:mm:ss"));
            dest.setHandleDate(DateUtils.convertToString(handleDatetime, "yyyy-MM-dd"));
            dest.setHandleTime(DateUtils.convertToString(handleDatetime, "HH:mm:ss"));
        }

        if(src.getCalleeReqDate() != null) {
            LocalDateTime calleeReqDate = src.getCalleeReqDate().toLocalDateTime();
            dest.setCalleeReqDate(DateUtils.convertToString(calleeReqDate, "yyyy-MM-dd"));
        }
    }
}
