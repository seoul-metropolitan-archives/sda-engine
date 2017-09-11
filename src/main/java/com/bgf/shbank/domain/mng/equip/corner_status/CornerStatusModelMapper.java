package com.bgf.shbank.domain.mng.equip.corner_status;

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
public class CornerStatusModelMapper extends CustomMapper<CornerStatus, CornerStatusVO> {

    @Override
    public void mapAtoB(CornerStatus src, CornerStatusVO dest, MappingContext context) {

        LocalDateTime operStartTime = src.getOperStartTime().toLocalDateTime();
        dest.setOperStartTime(DateUtils.convertToString(operStartTime, "HH:mm:ss"));

        LocalDateTime operEndTime = src.getOperEndTime().toLocalDateTime();
        dest.setOperEndTime(DateUtils.convertToString(operEndTime, "HH:mm:ss"));

        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setPlaceGubunName(CommonCodeUtils.getName("PLACE_GUBUN", src.getPlaceGubun()));

        dest.setBranchGubunName(CommonCodeUtils.getName("BRANCH_GUBUN", src.getBranchGubun()));
        dest.setSpecialStyleGubunName(CommonCodeUtils.getName("SPECIAL_STYLE_GUBUN", src.getSpecialStyleGubun()));
        dest.setOperTimeGubunName(CommonCodeUtils.getName("OPER_TIME_GUBUN", src.getOperTimeGubun()));
        dest.setCheckOperEnableName(CommonCodeUtils.getName("CHECK_OPER_ENABLE", src.getCheckOperEnable()));
        dest.setSecurityCorpCodeName(CommonCodeUtils.getName("SECURITY_CORP", src.getSecurityCorpCode()));
        dest.setFacHireEnableName(CommonCodeUtils.getName("FAC_HIRE_ENABLE", src.getFacHireEnable()));
    }
}