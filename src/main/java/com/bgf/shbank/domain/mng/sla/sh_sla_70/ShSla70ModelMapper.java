package com.bgf.shbank.domain.mng.sla.sh_sla_70;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by james on 2017-02-09.
 */
@Component
public class ShSla70ModelMapper extends CustomMapper<ShSla70, ShSla70VO> {

    @Override
    public void mapAtoB(ShSla70 src, ShSla70VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        if (src.getCalleeReqDatetime() != null) {
            LocalDateTime calleeReqDatetime = src.getCalleeReqDatetime().toLocalDateTime();
            dest.setCalleeReqDatetime(DateUtils.convertToString(calleeReqDatetime, "yyyy-MM-dd HH:mm:ss"));
        }

        if (src.getArrivalPlanDatetime() != null) {
            LocalDateTime arrivalPlanDatetime = src.getArrivalPlanDatetime().toLocalDateTime();
            dest.setArrivalPlanDatetime(DateUtils.convertToString(arrivalPlanDatetime, "yyyy-MM-dd HH:mm:ss"));
        }

        if (src.getArrivalDatetime() != null) {
            LocalDateTime arrivalDatetime = src.getArrivalDatetime().toLocalDateTime();
            dest.setArrivalDatetime(DateUtils.convertToString(arrivalDatetime, "yyyy-MM-dd HH:mm:ss"));
        }

        if (src.getReportDatetime() != null) {
            LocalDateTime reportDatetime = src.getReportDatetime().toLocalDateTime();
            dest.setReportDatetime(DateUtils.convertToString(reportDatetime, "yyyy-MM-dd HH:mm:ss"));
        }
    }
}

