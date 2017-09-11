package com.bgf.shbank.domain.mng.equip.sh02001120;

import io.onsemiro.utils.CommonCodeUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by jung-young-il on 27/02/2017.
 */

@Component
public class Sh02001120ModelMapper extends CustomMapper<Sh02001120, Sh02001120VO> {

    @Override
    public void mapAtoB(Sh02001120 src, Sh02001120VO dest, MappingContext context) {

        dest.setStextGubun(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
        dest.setClosingBranchNo(CommonCodeUtils.getName("BRANCH_CODE", src.getClosingBranchNo()));

//        dest.setClosingCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getClosingCornerCode()));
    }
}
