package com.bgf.shbank.domain.mng.etc.sh04001190;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh04001190ModelMapper extends CustomMapper<Sh04001190, Sh04001190VO> {

    @Override
    public void mapAtoB(Sh04001190 src, Sh04001190VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

    }
}
