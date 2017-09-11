package com.bgf.shbank.domain.mng.equip.sh02001230;

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
public class Sh02001230ModelMapper extends CustomMapper<Sh02001230, Sh02001230VO> {

    @Override
    public void mapAtoB(Sh02001230 src, Sh02001230VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

        if (src.getChangeDatetime() != null) {

            LocalDateTime changeDatetime = src.getChangeDatetime().toLocalDateTime();
            dest.setChangeDatetime(DateUtils.convertToString(changeDatetime, "yyyy-MM-dd HH:mm:ss"));

            LocalDateTime changeDate = src.getChangeDatetime().toLocalDateTime();
            dest.setChangeDate(DateUtils.convertToString(changeDate, "yyyy-MM-dd"));

            LocalDateTime changeTime = src.getChangeDatetime().toLocalDateTime();
            dest.setChangeTime(DateUtils.convertToString(changeTime, "HH:mm:ss"));
        }

        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
//        dest.setCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getCornerCode()));

    }
}
