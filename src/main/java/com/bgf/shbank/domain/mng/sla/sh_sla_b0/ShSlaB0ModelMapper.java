package com.bgf.shbank.domain.mng.sla.sh_sla_b0;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by james on 2017-02-09.
 */
@Component
public class ShSlaB0ModelMapper extends CustomMapper<ShSlaB0, ShSlaB0VO> {

    @Override
    public void mapAtoB(ShSlaB0 src, ShSlaB0VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        if (src.getOverhaulDatetime() != null) {
            LocalDateTime overhaulDatetime = src.getOverhaulDatetime().toLocalDateTime();
            dest.setOverhaulDatetime(DateUtils.convertToString(overhaulDatetime, "yyyy-MM-dd HH:mm:ss"));
        }
    }
}

