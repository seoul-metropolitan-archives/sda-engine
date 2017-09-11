package com.bgf.shbank.domain.mng.equip.sh05001130;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 29/03/2017.
 */
@Component
public class Sh05001130ModelMapper extends CustomMapper<Sh05001130, Sh05001130VO> {
    @Override
    public void mapAtoB(Sh05001130 src, Sh05001130VO dest, MappingContext context) {

        LocalDateTime overhaulDate = src.getOverhaulDate().toLocalDateTime();
        dest.setOverhaulDate(DateUtils.convertToString(overhaulDate, "yyyy-MM-dd"));
    }
}