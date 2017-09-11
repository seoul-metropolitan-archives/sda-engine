package com.bgf.shbank.domain.mng.error.sh01001240;

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
public class Sh01001240ModelMapper extends CustomMapper<Sh01001240, Sh01001240VO> {

    @Override
    public void mapAtoB(Sh01001240 src, Sh01001240VO dest, MappingContext context) {

        LocalDateTime handleDatetime = src.getHandleDatetime().toLocalDateTime();
        dest.setHandleDatetime(DateUtils.convertToString(handleDatetime, "yyyy-MM-dd HH:mm:ss"));


        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        dest.setCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getCornerCode()));
        dest.setHandleStatus(CommonCodeUtils.getName("HANDLE_STATUS", src.getHandleStatus()));


    }
}