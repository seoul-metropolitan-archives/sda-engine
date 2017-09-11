package com.bgf.shbank.domain.mng.sla.sh_sla_a0;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on A017-02-09.
 */
@Component
public class ShSlaA0ModelMapper extends CustomMapper<ShSlaA0, ShSlaA0VO> {

    @Override
    public void mapAtoB(ShSlaA0 src, ShSlaA0VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime regDatetime = src.getRegDatetime().toLocalDateTime();
        dest.setRegDatetime(DateUtils.convertToString(regDatetime, "yyyy-MM-dd HH:mm:ss"));

        if (src.getOverhaulDate() != null) {
            LocalDateTime overhaulDatetime = src.getOverhaulDate().toLocalDateTime();
            dest.setOverhaulDate(DateUtils.convertToString(overhaulDatetime, "yyyy-MM-dd"));
        }

        if (src.getOperDate() != null) {
            LocalDateTime operDatetime = src.getOperDate().toLocalDateTime();
            dest.setOperDate(DateUtils.convertToString(operDatetime, "yyyy-MM-dd"));
        }
    }
}

