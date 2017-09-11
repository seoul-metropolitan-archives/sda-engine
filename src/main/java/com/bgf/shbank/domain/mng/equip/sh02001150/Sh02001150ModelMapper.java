package com.bgf.shbank.domain.mng.equip.sh02001150;

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
public class Sh02001150ModelMapper extends CustomMapper<Sh02001150, Sh02001150VO> {

    @Override
    public void mapAtoB(Sh02001150 src, Sh02001150VO dest, MappingContext context) {

        LocalDateTime workCompleteDate = src.getWorkCompleteDate().toLocalDateTime();
        dest.setWorkCompleteDate(DateUtils.convertToString(workCompleteDate, "yyyy-MM-dd"));

        dest.setResultStextGubunName(CommonCodeUtils.getName("RESULT_STEXT_GUBUN", src.getResultStextGubun()));
        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        //dest.setCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getCornerCode()));
        dest.setTerminalCorpCodeName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getTerminalCorpCode()));
        String modeCode = src.getTerminalCorpCode() + src.getModelCode();
        dest.setModelCodeName(CommonCodeUtils.getName("MODEL_CODE", modeCode));
        dest.setWorkCompleteEnableName(CommonCodeUtils.getName("WORK_COMPLETE_ENABLE", src.getWorkCompleteEnable()));
    }
}
