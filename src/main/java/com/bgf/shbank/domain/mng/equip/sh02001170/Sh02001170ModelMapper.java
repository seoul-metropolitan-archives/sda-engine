package com.bgf.shbank.domain.mng.equip.sh02001170;

/**
 * Created by jung-young-il on 27/02/2017.
 */

import io.onsemiro.utils.CommonCodeUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by jung-young-il on 27/02/2017.
 */
@Component
public class Sh02001170ModelMapper  extends CustomMapper<Sh02001170, Sh02001170VO> {

    @Override
    public void mapAtoB(Sh02001170 src, Sh02001170VO dest, MappingContext context) {

        dest.setStextGubunName(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
        dest.setJisaCode(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        //dest.setCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getCornerCode()));

        dest.setFacGubunCodeName(CommonCodeUtils.getName("FAC_GUBUN_CODE", src.getFacGubunCode()));
        dest.setFacCodeName(CommonCodeUtils.getName("FAC_CODE", src.getFacCode()));
        dest.setHireFacEnableName(CommonCodeUtils.getName("HIRE_FAC_ENABLE", src.getHireFacEnable()));
        dest.setNouseGubunName(CommonCodeUtils.getName("NOUSE_GUBUN", src.getNouseGubun()));

    }
}
