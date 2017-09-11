package com.bgf.shbank.domain.mng.equip.sh02001180;

import io.onsemiro.utils.CommonCodeUtils;
import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh02001180ModelMapper extends CustomMapper<Sh02001180, Sh02001180VO> {

    @Override
    public void mapAtoB(Sh02001180 src, Sh02001180VO dest, MappingContext context) {

        if (src.getAdoptDate() != null) {
            LocalDateTime adoptDate = src.getAdoptDate().toLocalDateTime();
            dest.setAdoptDate(DateUtils.convertToString(adoptDate, "yyyy-MM-dd HH:mm:ss"));
        }


        if (src.getWorkCompleteDate() != null) {
            LocalDateTime workCompleteDate = src.getWorkCompleteDate().toLocalDateTime();
            dest.setWorkCompleteDate(DateUtils.convertToString(workCompleteDate, "yyyy-MM-dd HH:mm:ss"));
        }


        dest.setResultStextGubunName(CommonCodeUtils.getName("RESULT_STEXT_GUBUN", src.getResultStextGubun()));
        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
//        dest.setCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getCornerCode()));
        if (src.getWorkCompleteEnable() != null) {
            dest.setWorkCompleteEnableName(CommonCodeUtils.getName("WORK_COMPLETE_ENABLE", src.getWorkCompleteEnable()));
        }

    }
}
