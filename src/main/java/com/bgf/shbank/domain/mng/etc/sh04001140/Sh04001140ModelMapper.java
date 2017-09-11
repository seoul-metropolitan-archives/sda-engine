package com.bgf.shbank.domain.mng.etc.sh04001140;

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
public class Sh04001140ModelMapper extends CustomMapper<Sh04001140, Sh04001140VO> {

    @Override
    public void mapAtoB(Sh04001140 src, Sh04001140VO dest, MappingContext context) {

        LocalDateTime overhaulDate = src.getOverhaulDate().toLocalDateTime();
        dest.setOverhaulDate(DateUtils.convertToString(overhaulDate, "yyyy-MM-dd"));

        LocalDateTime overhaulStartTime = src.getOverhaulStartTime().toLocalDateTime();
        dest.setOverhaulStartTime(DateUtils.convertToString(overhaulStartTime, "HH:mm:ss"));

        LocalDateTime overhaulEndTime = src.getOverhaulEndTime().toLocalDateTime();
        dest.setOverhaulEndTime(DateUtils.convertToString(overhaulEndTime, "HH:mm:ss"));

        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        if (src.getMngBranchCode() != null && !src.getMngBranchCode().isEmpty()) {
            dest.setMngBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getMngBranchCode()));
        }

        String modelCode = src.getOverhaulCorp() + src.getModelCode();
        dest.setModelCodeName(CommonCodeUtils.getName("MODEL_CODE", modelCode));

        dest.setOverhaulCorpName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getOverhaulCorp()));
        dest.setInspectionCorpName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getInspectionCorp()));

        dest.setOverhaulGubunName(CommonCodeUtils.getName("OVERHAUL_GUBUN", src.getOverhaulGubun()));
    }
}
