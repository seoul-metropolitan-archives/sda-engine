package com.bgf.shbank.domain.mng.equip.sh02001270;

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
public class Sh02001270ModelMapper extends CustomMapper<Sh02001270, Sh02001270VO> {

    @Override
    public void mapAtoB(Sh02001270 src, Sh02001270VO dest, MappingContext context) {

        LocalDateTime closingDate = src.getClosingDate().toLocalDateTime();
        dest.setClosingDate(DateUtils.convertToString(closingDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime restartDate = src.getRestartDate().toLocalDateTime();
        dest.setRestartDate(DateUtils.convertToString(restartDate, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime operStartTime = src.getOperStartTime().toLocalDateTime();
        dest.setOperStartTime(DateUtils.convertToString(operStartTime, "HH:mm:ss"));

        LocalDateTime operEndTime = src.getOperEndTime().toLocalDateTime();
        dest.setOperEndTime(DateUtils.convertToString(operEndTime, "HH:mm:ss"));


        dest.setStextGubun(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));

        dest.setRestartGubun(CommonCodeUtils.getName("RESTART_GUBUN", src.getRestartGubun()));

        dest.setChangeItemGubun(CommonCodeUtils.getName("CHANGE_ITEM_GUBUN", src.getChangeItemGubun()));
        dest.setCheckOper(CommonCodeUtils.getName("CHECK_OPER", src.getCheckOper()));

    }
}