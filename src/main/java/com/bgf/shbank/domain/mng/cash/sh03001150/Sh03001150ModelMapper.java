package com.bgf.shbank.domain.mng.cash.sh03001150;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001150ModelMapper extends CustomMapper<Sh03001150, Sh03001150VO> {

    @Override
    public void mapAtoB(Sh03001150 src, Sh03001150VO dest, MappingContext context) {

        LocalDateTime dealDate = src.getDealDate().toLocalDateTime();
        dest.setDealDate(DateUtils.convertToString(dealDate, "yyyy-MM-dd"));

        LocalDateTime processDate = src.getProcessDate().toLocalDateTime();
        dest.setProcessDate(DateUtils.convertToString(processDate, "yyyy-MM-dd"));

    }
}