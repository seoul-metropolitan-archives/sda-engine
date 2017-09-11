package com.bgf.shbank.domain.mng.cash.sh03001190;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001190ModelMapper extends CustomMapper<Sh03001190, Sh03001190VO> {

    @Override
    public void mapAtoB(Sh03001190 src, Sh03001190VO dest, MappingContext context) {

        if(src.getTxId() != null) {
            LocalDateTime txId = src.getTxId().toLocalDateTime();
            dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));
        }

        LocalDateTime reqDate = src.getReqDate().toLocalDateTime();
        dest.setReqDate(DateUtils.convertToString(reqDate, "yyyy-MM-dd"));

        LocalDateTime fundExpenseStartDate = src.getFundExpenseStartDate().toLocalDateTime();
        dest.setFundExpenseStartDate(DateUtils.convertToString(fundExpenseStartDate, "yyyy-MM-dd"));

        LocalDateTime fundExpenseEndDate = src.getFundExpenseEndDate().toLocalDateTime();
        dest.setFundExpenseEndDate(DateUtils.convertToString(fundExpenseEndDate, "yyyy-MM-dd"));
    }
}