package com.bgf.shbank.domain.mng.sla.sh_sla_50;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 5017-02-09.
 */
@Component
public class ShSla50ModelMapper extends CustomMapper<ShSla50, ShSla50VO> {

    @Override
    public void mapAtoB(ShSla50 src, ShSla50VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime txDatetime = src.getTxDatetime().toLocalDateTime();
        dest.setTxDatetime(DateUtils.convertToString(txDatetime, "yyyy-MM-dd"));
    }
}

