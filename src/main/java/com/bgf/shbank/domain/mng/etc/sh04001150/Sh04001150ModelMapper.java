package com.bgf.shbank.domain.mng.etc.sh04001150;

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
public class Sh04001150ModelMapper extends CustomMapper<Sh04001150, Sh04001150VO> {

    @Override
    public void mapAtoB(Sh04001150 src, Sh04001150VO dest, MappingContext context) {

        LocalDateTime errorDatetime = src.getErrorDatetime().toLocalDateTime();
        dest.setErrorDatetime(DateUtils.convertToString(errorDatetime, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime repairDatetime = src.getRepairDatetime().toLocalDateTime();
        dest.setRepairDatetime(DateUtils.convertToString(repairDatetime, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime cornerArrivalDate = src.getCornerArrivalDatetime().toLocalDateTime();
        dest.setCornerArrivalDate(DateUtils.convertToString(cornerArrivalDate, "yyyy-MM-dd"));

        LocalDateTime cornerArrivalTime = src.getCornerArrivalDatetime().toLocalDateTime();
        dest.setCornerArrivalTime(DateUtils.convertToString(cornerArrivalTime, "HH:mm:ss"));

        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));

        dest.setCalleeChasuGubunName(CommonCodeUtils.getName("CALLEE_CHASU_GUBUN", src.getCalleeChasuGubun()));

        dest.setNonePenaltyEnableName(CommonCodeUtils.getName("NONE_PENALTY_ENABLE", src.getNonePenaltyEnable()));
    }
}
