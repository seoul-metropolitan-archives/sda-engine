package com.bgf.shbank.domain.mng.sla.sh_sla_g1;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on G117-02-09.
 */
@Component
public class ShSlaG1ModelMapper extends CustomMapper<ShSlaG1, ShSlaG1VO> {

    @Override
    public void mapAtoB(ShSlaG1 src, ShSlaG1VO dest, MappingContext context) {

        LocalDateTime txDate = src.getTxDate().toLocalDateTime();
        dest.setTxDate(DateUtils.convertToString(txDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime withdrawDatetime = src.getWithdrawDatetime().toLocalDateTime();
        dest.setWithdrawDatetime(DateUtils.convertToString(withdrawDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setWithdrawDate(DateUtils.convertToString(withdrawDatetime, "yyyy-MM-dd"));
        dest.setWithdrawTime(DateUtils.convertToString(withdrawDatetime, "HH:mm:ss"));

        LocalDateTime transferDatetime = src.getTransferDatetime().toLocalDateTime();
        dest.setTransferDatetime(DateUtils.convertToString(transferDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setTransferDate(DateUtils.convertToString(transferDatetime, "yyyy-MM-dd"));
        dest.setTransferTime(DateUtils.convertToString(transferDatetime, "HH:mm:ss"));

        LocalDateTime returnDatetime = src.getReturnDatetime().toLocalDateTime();
        dest.setReturnDatetime(DateUtils.convertToString(returnDatetime, "yyyy-MM-dd HH:mm:ss"));
        dest.setReturnDate(DateUtils.convertToString(returnDatetime, "yyyy-MM-dd"));
        dest.setReturnTime(DateUtils.convertToString(returnDatetime, "HH:mm:ss"));
    }
}

