package com.bgf.shbank.domain.mng.equip.terminal_status;

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
public class TerminalStatusModelMapper extends CustomMapper<TerminalStatus, TerminalStatusVO> {

    @Override
    public void mapAtoB(TerminalStatus src, TerminalStatusVO dest, MappingContext context) {

        if (src.getOperStartTime() != null) {
            LocalDateTime operStartTime = src.getOperStartTime().toLocalDateTime();
            dest.setOperStartTime(DateUtils.convertToString(operStartTime, "HH:mm:ss"));
        }

        if (src.getOperEndTime() != null) {
            LocalDateTime operEndTime = src.getOperEndTime().toLocalDateTime();
            dest.setOperEndTime(DateUtils.convertToString(operEndTime, "HH:mm:ss"));
        }

        if (src.getTaskApplyDate() != null) {
            LocalDateTime taskApplyDate = src.getTaskApplyDate().toLocalDateTime();
            dest.setTaskApplyDate(DateUtils.convertToString(taskApplyDate, "yyyy-MM-dd"));
        }

        if (src.getWorkDate() != null) {
            LocalDateTime workDate = src.getWorkDate().toLocalDateTime();
            dest.setWorkDate(DateUtils.convertToString(workDate, "yyyy-MM-dd"));
        }

        String modelCode = src.getTerminalCorpCode() + src.getModelCode();
        dest.setModelCode(modelCode);

        dest.setSecurityCorp(StringUtils.trim(src.getSecurityCorp()));
        dest.setInstallPlaceGubun(StringUtils.trim(src.getInstallPlaceGubun()));
        dest.setBoothType(StringUtils.trim(src.getBoothType()));

        //dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        //dest.setJisaCodeName(src.getJisaCode());

        /*dest.setOperEnableName(CommonCodeUtils.getName("OPER_ENABLE", src.getOperEnable()));

        dest.setModelCodeName(CommonCodeUtils.getName("MODEL_CODE", modelCode));

        dest.setBranchGubunName(CommonCodeUtils.getName("BRANCH_GUBUN", src.getBranchGubun()));
        dest.setTerminalTypeName(CommonCodeUtils.getName("TERMINAL_TYPE", src.getTerminalType()));
        dest.setPlaceGubunName(CommonCodeUtils.getName("PLACE_GUBUN", src.getPlaceGubun()));
        dest.setOperTimeGubunName(CommonCodeUtils.getName("OPER_TIME_GUBUN", src.getOperTimeGubun()));
        dest.setWeekendOperGubunName(CommonCodeUtils.getName("WEEKEND_OPER_GUBUN", src.getWeekendOperGubun()));

        dest.setCheckOperEnableName(CommonCodeUtils.getName("CHECK_OPER_ENABLE", src.getCheckOperEnable()));
        dest.setCash50kOperEnableName(CommonCodeUtils.getName("CASH_50K_TERMINAL_OPER_ENABLE", src.getCash50kOperEnable()));
        dest.setSecurityCorpName(CommonCodeUtils.getName("SECURITY_CORP", src.getSecurityCorp()));

        dest.setInstallPlaceGubunName(CommonCodeUtils.getName("INSTALL_PLACE_GUBUN", src.getInstallPlaceGubun()));

        dest.setPhotoEnableName(CommonCodeUtils.getName("TAKE_PHOTO_ENABLE", src.getPhotoEnable()));
        dest.setBoothCorpName(CommonCodeUtils.getName("BOOTH_CORP", src.getBoothCorp()));
        dest.setBoothTypeName(CommonCodeUtils.getName("BOOTH_TYPE", src.getBoothType()));
        dest.setIntercomEnableName(CommonCodeUtils.getName("INTERCOM_ENABLE", src.getIntercomEnable()));
        dest.setEnvelopeEnableName(CommonCodeUtils.getName("ENVELOPE_ENABLE", src.getEnvelopeEnable()));
        dest.setGarbagecanEnableName(CommonCodeUtils.getName("GARBAGECAN_ENABLE", src.getGarbagecanEnable()));
        dest.setShredderEnableName(CommonCodeUtils.getName("SHREDDER_ENABLE", src.getShredderEnable()));

        dest.setExtinguisherEnableName(CommonCodeUtils.getName("EXTINGUISHER_ENABLE", src.getExtinguisherEnable()));
        dest.setCoolerHeaterEnableName(CommonCodeUtils.getName("COOLER_HEATER_ENABLE", src.getCoolerHeaterEnable()));

        dest.setSlopeEnableName(CommonCodeUtils.getName("SLOPE_ENABLE", src.getSlopeEnable()));
        dest.setTerminalCorpCodeName(CommonCodeUtils.getName("TERMINAL_CORP_CODE", src.getTerminalCorpCode()));
        if (src.getHireTerminalEnable() != null && !src.getHireTerminalEnable().isEmpty()) {
            dest.setHireTerminalEnableName(CommonCodeUtils.getName("HIRE_TERMINAL_ENABLE", src.getHireTerminalEnable()));
        }

        if (src.getInstallTerminalGubun() != null &&!src.getInstallTerminalGubun().isEmpty()) {
            dest.setInstallTerminalGubunName(CommonCodeUtils.getName("INSTALL_TERMINAL_GUBUN", src.getInstallTerminalGubun()));
        }*/

    }


}