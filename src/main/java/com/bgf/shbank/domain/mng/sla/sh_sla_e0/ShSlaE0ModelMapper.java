package com.bgf.shbank.domain.mng.sla.sh_sla_e0;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class ShSlaE0ModelMapper extends CustomMapper<ShSlaE0, ShSlaE0VO> {

    @Override
    public void mapAtoB(ShSlaE0 src, ShSlaE0VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime stndDatetime = src.getStndDate().toLocalDateTime();
        dest.setStndDate(DateUtils.convertToString(stndDatetime, "yyyy-MM"));
    }
}

