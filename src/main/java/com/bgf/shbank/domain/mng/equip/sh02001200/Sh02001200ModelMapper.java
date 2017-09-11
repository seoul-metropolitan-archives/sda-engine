package com.bgf.shbank.domain.mng.equip.sh02001200;

import io.onsemiro.utils.CommonCodeUtils;
import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh02001200ModelMapper extends CustomMapper<Sh02001200, Sh02001200VO> {

    @Override
    public void mapAtoB(Sh02001200 src, Sh02001200VO dest, MappingContext context) {

        LocalDateTime stextNoticeDatetime = src.getStextNoticeDatetime().toLocalDateTime();
        dest.setStextNoticeDatetime(DateUtils.convertToString(stextNoticeDatetime, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime operStartTime = src.getOperStartTime().toLocalDateTime();
        dest.setOperStartTime(DateUtils.convertToString(operStartTime, "HH:mm:ss"));

        LocalDateTime operEndTime = src.getOperEndTime().toLocalDateTime();
        dest.setOperEndTime(DateUtils.convertToString(operEndTime, "HH:mm:ss"));

        dest.setStextGubun(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        dest.setPlaceGubun(CommonCodeUtils.getName("PLACE_GUBUN", src.getPlaceGubun()));
        dest.setBranchGubun(CommonCodeUtils.getName("BRANCH_GUBUN", src.getBranchGubun()));

        dest.setOperStartGubun(CommonCodeUtils.getName("OPER_TIME_GUBUN", src.getOperStartGubun()));
        dest.setCheckOperEnable(CommonCodeUtils.getName("CHECK_OPER_ENABLE", src.getCheckOperEnable()));

        if (StringUtils.isNotEmpty(src.getSecurityCorpCode())) {
            if (src.getSecurityCorpCode().equals("02") || src.getSecurityCorpCode().equals("2") || src.getSecurityCorpCode().equals("31") || src.getSecurityCorpCode().equals("36")) {
                // S1
                dest.setSecurityCorpCode(CommonCodeUtils.getName("SECURITY_CORP", "2"));
            } else if (src.getSecurityCorpCode().equals("32") || src.getSecurityCorpCode().equals("34")) {
                // CAPS
                dest.setSecurityCorpCode(CommonCodeUtils.getName("SECURITY_CORP", "1"));
            } else if (src.getSecurityCorpCode().equals("35")) {
                // KT 텔레캅
                dest.setSecurityCorpCode(CommonCodeUtils.getName("SECURITY_CORP", "3"));
            }
        }
    }
}
