package com.bgf.shbank.domain.mng.cash.sh03001230;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001230ModelMapper extends CustomMapper<Sh03001230, Sh03001230VO> {

    @Override
    public void mapAtoB(Sh03001230 src, Sh03001230VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime rtnDate = src.getRtnDate().toLocalDateTime();
        dest.setRtnDate(DateUtils.convertToString(rtnDate, "yyyy-MM-dd"));

        LocalDateTime confirmDate = src.getConfirmDate().toLocalDateTime();
        dest.setConfirmDate(DateUtils.convertToString(confirmDate, "yyyy-MM-dd"));

        LocalDateTime dealDate = src.getDealDate().toLocalDateTime();
        dest.setDealDate(DateUtils.convertToString(dealDate, "yyyy-MM-dd"));
    }
}