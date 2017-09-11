package com.bgf.shbank.domain.mng.sla.sh_sla_30;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 3017-02-09.
 */
@Component
public class ShSla30ModelMapper extends CustomMapper<ShSla30, ShSla30VO> {

    @Override
    public void mapAtoB(ShSla30 src, ShSla30VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime minwonDatetime = src.getMinwonDatetime().toLocalDateTime();
        dest.setMinwonDatetime(DateUtils.convertToString(minwonDatetime, "yyyy-MM-dd"));

        if (src.getRegDatetime() != null) {
            LocalDateTime regDatetime = src.getRegDatetime().toLocalDateTime();
            dest.setRegDatetime(DateUtils.convertToString(regDatetime, "yyyy-MM-dd"));
        }
    }
}

