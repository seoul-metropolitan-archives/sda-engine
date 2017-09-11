package com.bgf.shbank.domain.mng.etc.sh04001130;

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
public class Sh04001130ModelMapper extends CustomMapper<Sh04001130, Sh04001130VO> {

    @Override
    public void mapAtoB(Sh04001130 src, Sh04001130VO dest, MappingContext context) {

        LocalDateTime serviceFeeCalcYearMonth = src.getServiceFeeCalcYearMonth().toLocalDateTime();
        dest.setServiceFeeCalcYearMonth(DateUtils.convertToString(serviceFeeCalcYearMonth, "yyyy-MM-dd"));

        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));

        dest.setBranchGubunCodeName(CommonCodeUtils.getName("BRANCH_GUBUN", src.getBranchGubunCode()));

        dest.setOperTimeGubunCodeName(CommonCodeUtils.getName("OPER_TIME_GUBUN", src.getOperTimeGubunCode()));
        dest.setCheckOperEnableName(CommonCodeUtils.getName("CHECK_OPER_ENABLE", src.getCheckOperEnable()));
    }
}
