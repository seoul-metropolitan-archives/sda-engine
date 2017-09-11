package com.bgf.shbank.domain.mng.equip.sh02001280;

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
public class Sh02001280ModelMapper extends CustomMapper<Sh02001280, Sh02001280VO> {

    @Override
    public void mapAtoB(Sh02001280 src, Sh02001280VO dest, MappingContext context) {

        LocalDateTime workDate = src.getWorkDate().toLocalDateTime();
        dest.setWorkDate(DateUtils.convertToString(workDate, "yyyy-MM-dd HH:mm:ss"));

        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));

        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
    }
}
