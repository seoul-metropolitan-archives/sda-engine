package com.bgf.shbank.domain.mng.sla.sh_sla_60;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 6017-02-09.
 */
@Component
public class ShSla60ModelMapper extends CustomMapper<ShSla60, ShSla60VO> {

    @Override
    public void mapAtoB(ShSla60 src, ShSla60VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime regDatetime = src.getRegDatetime().toLocalDateTime();
        dest.setRegDatetime(DateUtils.convertToString(regDatetime, "yyyy-MM-dd"));
    }
}

