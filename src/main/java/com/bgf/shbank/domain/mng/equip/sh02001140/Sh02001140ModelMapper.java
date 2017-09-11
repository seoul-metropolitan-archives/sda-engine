package com.bgf.shbank.domain.mng.equip.sh02001140;

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
public class Sh02001140ModelMapper extends CustomMapper<Sh02001140, Sh02001140VO> {

    @Override
    public void mapAtoB(Sh02001140 src, Sh02001140VO dest, MappingContext context) {

        LocalDateTime workPlanDatetime = src.getWorkPlanDatetime().toLocalDateTime();
        dest.setWorkPlanDatetime(DateUtils.convertToString(workPlanDatetime, "yyyy-MM-dd HH:mm:ss"));

        dest.setStextGubunName(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));

        dest.setNouseJisaCodeName((CommonCodeUtils.getName("JISA_CODE", src.getNouseJisaCode())));
        dest.setNouseBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getNouseBranchCode()));
        //dest.setNouseCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getNouseCornerCode()));
        dest.setTerminalCorpCodeName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getTerminalCorpCode()));

        String modelCode = src.getTerminalCorpCode() + src.getModelCode();
        dest.setModelCodeName(CommonCodeUtils.getName("MODEL_CODE", modelCode));
        dest.setHireTerminalEnableName(CommonCodeUtils.getName("NOUSE_HIRE_TERMINAL_ENABLE", src.getHireTerminalEnable()));
        dest.setNouseGubunName(CommonCodeUtils.getName("NOUSE_GUBUN", src.getNouseGubun()));

    }
}
