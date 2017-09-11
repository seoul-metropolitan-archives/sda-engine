package com.bgf.shbank.domain.mng.sla.sh_sla_10;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class ShSla10ModelMapper extends CustomMapper<ShSla10, ShSla10VO> {

    @Override
    public void mapAtoB(ShSla10 src, ShSla10VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        if (src.getCalleeReqDatetime() != null) {
            LocalDateTime calleeReqDatetime = src.getCalleeReqDatetime().toLocalDateTime();
            dest.setCalleeReqDatetime(DateUtils.convertToString(calleeReqDatetime, "yyyy-MM-dd HH:mm:ss"));
        }

        if (src.getArrivalDatetime() != null) {
            LocalDateTime arrivalDatetime = src.getArrivalDatetime().toLocalDateTime();
            dest.setArrivalDatetime(DateUtils.convertToString(arrivalDatetime, "yyyy-MM-dd HH:mm:ss"));
        }


    }
}

