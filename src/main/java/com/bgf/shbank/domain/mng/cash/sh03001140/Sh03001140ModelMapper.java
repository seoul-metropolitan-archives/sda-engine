package com.bgf.shbank.domain.mng.cash.sh03001140;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001140ModelMapper extends CustomMapper<Sh03001140, Sh03001140VO> {

    @Override
    public void mapAtoB(Sh03001140 src, Sh03001140VO dest, MappingContext context) {

        LocalDateTime closeDate = src.getCloseDate().toLocalDateTime();
        dest.setCloseDate(DateUtils.convertToString(closeDate, "yyyy-MM-dd"));

    }
}