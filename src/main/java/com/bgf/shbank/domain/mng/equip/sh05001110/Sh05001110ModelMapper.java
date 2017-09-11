package com.bgf.shbank.domain.mng.equip.sh05001110;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 29/03/2017.
 */
@Component
public class Sh05001110ModelMapper extends CustomMapper<Sh05001110, Sh05001110VO> {
    @Override
    public void mapAtoB(Sh05001110 src, Sh05001110VO dest, MappingContext context) {

        LocalDateTime overhaulDate = src.getOverhaulDate().toLocalDateTime();
        dest.setOverhaulDate(DateUtils.convertToString(overhaulDate, "yyyy-MM-dd"));
    }
}