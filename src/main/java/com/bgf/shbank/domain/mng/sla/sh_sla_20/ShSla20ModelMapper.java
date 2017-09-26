package com.bgf.shbank.domain.mng.sla.sh_sla_20;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by james on 2017-02-09.
 */
@Component
public class ShSla20ModelMapper extends CustomMapper<ShSla20, ShSla20VO> {

    @Override
    public void mapAtoB(ShSla20 src, ShSla20VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        if (src.getRecoveryDatetime() != null) {
            LocalDateTime recoveryDatetime = src.getRecoveryDatetime().toLocalDateTime();
            dest.setRecoveryDatetime(DateUtils.convertToString(recoveryDatetime, "yyyy-MM-dd HH:mm:ss"));
        }
    }
}

