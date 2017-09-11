package com.bgf.shbank.domain.mng.error.sh01001230;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 28/02/2017.
 */
@Component
public class Sh01001230ModelMapper extends CustomMapper<Sh01001230, Sh01001230VO> {

    @Override
    public void mapAtoB(Sh01001230 src, Sh01001230VO dest, MappingContext context) {

        LocalDateTime handleOccurDatetime = src.getHandleOccurDatetime().toLocalDateTime();
        dest.setHandleOccurDatetime(DateUtils.convertToString(handleOccurDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setHandleOccurDate(DateUtils.convertToString(handleOccurDatetime, "yyyy-MM-dd"));
        dest.setHandleOccurTime(DateUtils.convertToString(handleOccurDatetime, "HH:mm:ss"));

        LocalDateTime handleNoticeDatetime = src.getHandleNoticeDatetime().toLocalDateTime();
        dest.setHandleNoticeDatetime(DateUtils.convertToString(handleNoticeDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setHandleNoticeDate(DateUtils.convertToString(handleNoticeDatetime, "yyyy-MM-dd"));
        dest.setHandleNoticeTime(DateUtils.convertToString(handleNoticeDatetime, "HH:mm:ss"));

        if (src.getHandleDatetime() != null) {
            LocalDateTime handleDatetime = src.getHandleDatetime().toLocalDateTime();
            dest.setHandleDatetime(DateUtils.convertToString(handleDatetime, "yyyy-MM-dd HH:mm:ss"));
            dest.setHandleDate(DateUtils.convertToString(handleDatetime, "yyyy-MM-dd"));
            dest.setHandleTime(DateUtils.convertToString(handleDatetime, "HH:mm:ss"));
        }
    }
}
