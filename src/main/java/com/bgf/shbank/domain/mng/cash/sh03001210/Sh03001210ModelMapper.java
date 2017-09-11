package com.bgf.shbank.domain.mng.cash.sh03001210;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001210ModelMapper extends CustomMapper<Sh03001210, Sh03001210VO> {

    @Override
    public void mapAtoB(Sh03001210 src, Sh03001210VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));
    }
}