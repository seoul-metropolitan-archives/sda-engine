package com.bgf.shbank.domain.mng.equip.sh02001110;

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
public class Sh02001110ModelMapper extends CustomMapper<Sh02001110, Sh02001110VO> {

    @Override
    public void mapAtoB(Sh02001110 src, Sh02001110VO dest, MappingContext context) {

        LocalDateTime operStartTime = src.getOperStartTime().toLocalDateTime();
        dest.setOperStartTime(DateUtils.convertToString(operStartTime, "HH:mm:ss"));

        LocalDateTime operEndTime = src.getOperEndTime().toLocalDateTime();
        dest.setOperEndTime(DateUtils.convertToString(operEndTime, "HH:mm:ss"));

        dest.setStextGubunName(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
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
