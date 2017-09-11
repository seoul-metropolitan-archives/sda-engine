package com.bgf.shbank.domain.mng.equip.sh02001160;

import io.onsemiro.utils.CommonCodeUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;


/**
 * Created by jung-young-il on 27/02/2017.
 */
@Component
public class Sh02001160ModelMapper  extends CustomMapper<Sh02001160, Sh02001160VO> {

    @Override
    public void mapAtoB(Sh02001160 src, Sh02001160VO dest, MappingContext context) {

        dest.setStextGubunName(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        //dest.setCornerCodeName(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getCornerCode()));

        dest.setFacGubunCodeName(CommonCodeUtils.getName("FAC_GUBUN_CODE", src.getFacGubunCode()));
        dest.setFacCodeName(CommonCodeUtils.getName("FAC_CODE", src.getFacCode()));
        dest.setHireFacEnableName(CommonCodeUtils.getName("HIRE_FAC_ENABLE", src.getHireFacEnable()));
        dest.setInstallArticleGubunName(CommonCodeUtils.getName("INSTALL_ARTICLE_GUBUN", src.getInstallArticleGubun()));

    }
}
