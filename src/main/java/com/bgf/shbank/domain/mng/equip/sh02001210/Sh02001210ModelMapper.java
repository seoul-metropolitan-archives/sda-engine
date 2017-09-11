package com.bgf.shbank.domain.mng.equip.sh02001210;

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
public class Sh02001210ModelMapper extends CustomMapper<Sh02001210, Sh02001210VO> {

    @Override
    public void mapAtoB(Sh02001210 src, Sh02001210VO dest, MappingContext context) {

        LocalDateTime workPlanDatetime = src.getWorkPlanDatetime().toLocalDateTime();
        dest.setWorkPlanDatetime(DateUtils.convertToString(workPlanDatetime, "yyyy-MM-dd HH:mm:ss"));


        dest.setStextGubunName(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));

        dest.setNouseJisaCodeName((CommonCodeUtils.getName("JISA_CODE", src.getNouseJisaCode())));
        dest.setNouseBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getNouseBranchCode()));
        //dest.setNouseCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getNouseCornerCode()));
        dest.setNouseTerminalCorpCodeName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getNouseTerminalCorpCode()));

        String modelCode = src.getNouseTerminalCorpCode() + src.getNouseModelCode();
        dest.setNouseModelCodeName(CommonCodeUtils.getName("MODEL_CODE", modelCode));
        dest.setNouseHireTerminalEnableName(CommonCodeUtils.getName("NOUSE_HIRE_TERMINAL_ENABLE", src.getNouseHireTerminalEnable()));
        dest.setNouseGubunName(CommonCodeUtils.getName("NOUSE_GUBUN", src.getNouseGubun()));

        dest.setNewBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getNewBranchCode()));
        //dest.setNewCornerCodeName(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getNewCornerCode()));
        dest.setNewTerminalCorpCodeName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getNewTerminalCorpCode()));

        String newModelCode = src.getNewTerminalCorpCode() + src.getNewModelCode();
        dest.setNewModelCodeName(CommonCodeUtils.getName("MODEL_CODE", newModelCode));
        dest.setNewHireTerminalEnableName(CommonCodeUtils.getName("NEW_HIRE_TERMINAL_ENABLE", src.getNewHireTerminalEnable()));
        dest.setNewInstallTerminalGubunName(CommonCodeUtils.getName("NEW_INSTALL_TERMINAL_GUBUN", src.getNewInstallTerminalGubun()));
    }
}
