package com.bgf.shbank.domain.mng.equip.sh02001130;

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
public class Sh02001130ModelMapper extends CustomMapper<Sh02001130, Sh02001130VO> {

    @Override
    public void mapAtoB(Sh02001130 src, Sh02001130VO dest, MappingContext context) {

        LocalDateTime workPlanDatetime = src.getWorkPlanDatetime().toLocalDateTime();
        dest.setWorkPlanDatetime(DateUtils.convertToString(workPlanDatetime, "yyyy-MM-dd HH:mm:ss"));

        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        //dest.setCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getCornerCode()));

        dest.setStextGubunName(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
        dest.setTerminalCorpCodeName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getTerminalCorpCode()));

        String modelCode = src.getTerminalCorpCode() + src.getModelCode();
        dest.setModelCodeName(CommonCodeUtils.getName("MODEL_CODE", modelCode));
        if (src.getHireTerminalEnable() != null && !src.getHireTerminalEnable().isEmpty()) {
            dest.setHireTerminalEnableName(CommonCodeUtils.getName("HIRE_TERMINAL_ENABLE", src.getHireTerminalEnable()));
        }
        if (src.getInstallTerminalGubun() != null && !src.getInstallTerminalGubun().isEmpty()) {
            dest.setInstallTerminalGubunName(CommonCodeUtils.getName("INSTALL_TERMINAL_GUBUN", src.getInstallTerminalGubun()));
        }


    }
}
