package com.bgf.shbank.domain.mng.cash.sh03001170;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001170ModelMapper extends CustomMapper<Sh03001170, Sh03001170VO> {

    @Override
    public void mapAtoB(Sh03001170 src, Sh03001170VO dest, MappingContext context) {

        if(src.getTxId()!=null) {
            LocalDateTime txId = src.getTxId().toLocalDateTime();
            dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));
        }

        if(src.getCashSendingDate()!=null) {
            LocalDateTime cashSendingDate = src.getCashSendingDate().toLocalDateTime();
            dest.setCashSendingDate(DateUtils.convertToString(cashSendingDate, "yyyy-MM-dd"));
        }
    }
}