package com.bgf.shbank.domain.mng.cash.sh03001200;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001200ModelMapper extends CustomMapper<Sh03001200, Sh03001200VO> {

    @Override
    public void mapAtoB(Sh03001200 src, Sh03001200VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));
    }
}