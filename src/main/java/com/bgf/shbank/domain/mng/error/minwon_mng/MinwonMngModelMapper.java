package com.bgf.shbank.domain.mng.error.minwon_mng;

import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class MinwonMngModelMapper extends CustomMapper<MinwonMng, MinwonMngVO> {

    @Override
    public void mapAtoB(MinwonMng src, MinwonMngVO dest, MappingContext context) {
        if(src.getRegDatetime() != null) {
            LocalDateTime regReqDatetime = src.getRegDatetime().toLocalDateTime();
            dest.setRegDatetime(DateUtils.convertToString(regReqDatetime, "yyyy-MM-dd HH:mm:ss"));
        }

        if(src.getUpdateDatetime() != null) {
            LocalDateTime updateDatetime = src.getUpdateDatetime().toLocalDateTime();
            dest.setUpdateDatetime(DateUtils.convertToString(updateDatetime, "yyyy-MM-dd HH:mm:ss"));
        }
    }
}

