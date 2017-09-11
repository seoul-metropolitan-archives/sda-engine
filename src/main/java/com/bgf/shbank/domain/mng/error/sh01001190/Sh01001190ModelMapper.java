package com.bgf.shbank.domain.mng.error.sh01001190;

import io.onsemiro.utils.CommonCodeUtils;
import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 28/02/2017.
 */
@Component
public class Sh01001190ModelMapper extends CustomMapper<Sh01001190, Sh01001190VO> {

    @Override
    public void mapAtoB(Sh01001190 src, Sh01001190VO dest, MappingContext context) {

        LocalDateTime handleDateTime = src.getHandleDatetime().toLocalDateTime();
        dest.setHandleDatetime(DateUtils.convertToString(handleDateTime, "yyyy-MM-dd"));

        LocalDateTime calleeReqDate = src.getCalleeReqDate().toLocalDateTime();
        dest.setCalleeReqDate(DateUtils.convertToString(calleeReqDate, "yyyy-MM-dd HH:mm:ss"));

        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        dest.setModelCode(CommonCodeUtils.getName("MODEL_CODE", src.getModelCode()));
        dest.setErrorMethod(CommonCodeUtils.getName("ERROR_METHOD", src.getErrorMethod()));

    }
}
