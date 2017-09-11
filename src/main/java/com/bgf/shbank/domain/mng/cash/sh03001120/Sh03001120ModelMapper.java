package com.bgf.shbank.domain.mng.cash.sh03001120;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001120ModelMapper extends CustomMapper<Sh03001120, Sh03001120VO> {

    @Override
    public void mapAtoB(Sh03001120 src, Sh03001120VO dest, MappingContext context) {

        if(src.getReferDate()!=null) {
            LocalDateTime referDate = src.getReferDate().toLocalDateTime();
            dest.setReferDate(DateUtils.convertToString(referDate, "yyyy-MM-dd"));
        }

        if(src.getDealTime()!=null) {
            LocalDateTime dealTime = src.getDealTime().toLocalDateTime();
            dest.setDealTime(DateUtils.convertToString(dealTime, "yyyy-MM-dd HH:mm:ss"));
        }
    }
}
