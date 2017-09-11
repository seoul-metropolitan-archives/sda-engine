package com.bgf.shbank.domain.mng.sla.sh_sla_40;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 4017-02-09.
 */
@Component
public class ShSla40ModelMapper extends CustomMapper<ShSla40, ShSla40VO> {

    @Override
    public void mapAtoB(ShSla40 src, ShSla40VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        if (src.getCalleeReqDatetime() != null) {
            LocalDateTime calleeReqDatetime = src.getCalleeReqDatetime().toLocalDateTime();
            dest.setCalleeReqDatetime(DateUtils.convertToString(calleeReqDatetime, "yyyy-MM-dd HH:mm:ss"));
        }

        if (src.getHappycallDatetime() != null) {
            LocalDateTime happyCallDatetime = src.getHappycallDatetime().toLocalDateTime();
            dest.setHappycallDatetime(DateUtils.convertToString(happyCallDatetime, "yyyy-MM-dd HH:mm:ss"));
        }

        if (src.getErrorDatetime() != null) {
            LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
            dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));
        }
    }
}

