package com.bgf.shbank.domain.mng.etc.sh04001110;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh04001110ModelMapper extends CustomMapper<Sh04001110, Sh04001110VO> {

    @Override
    public void mapAtoB(Sh04001110 src, Sh04001110VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime reqStextDate = src.getReqStextDate().toLocalDateTime();
        dest.setReqStextDate(DateUtils.convertToString(reqStextDate, "yyyy-MM-dd"));

    }
}
