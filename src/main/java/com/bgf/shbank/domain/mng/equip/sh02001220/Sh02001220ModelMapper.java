package com.bgf.shbank.domain.mng.equip.sh02001220;

import io.onsemiro.utils.CommonCodeUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;


/**
 * Created by jung-young-il on 28/02/2017.
 */
@Component
public class Sh02001220ModelMapper extends CustomMapper<Sh02001220, Sh02001220VO> {

    @Override
    public void mapAtoB(Sh02001220 src, Sh02001220VO dest, MappingContext context) {

        dest.setStextGubun(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));

        dest.setJisaCode((CommonCodeUtils.getName("JISA_CODE", src.getJisaCode())));
        dest.setNouseBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getNouseBranchCode()));
        dest.setNouseCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getNouseCornerCode()));
        dest.setNouseFacGubunCode(CommonCodeUtils.getName("FAC_GUBUN_CODE", src.getNouseFacGubunCode()));
        dest.setNouseFacCode(CommonCodeUtils.getName("FAC_CODE", src.getNouseFacCode()));
        dest.setNouseGubun(CommonCodeUtils.getName("NOUSE_GUBUN", src.getNouseGubun()));

        dest.setNewBranchCode(CommonCodeUtils.getName("BRANCH_CODE", src.getNewBranchCode()));
        dest.setNewCornerCode(CommonCodeUtils.getName("CORNER_TERMINAL_CODE", src.getNewCornerCode()));
        dest.setNewFacGubunCode(CommonCodeUtils.getName("FAC_GUBUN_CODE", src.getNewFacGubunCode()));
        dest.setNewFacCode(CommonCodeUtils.getName("FAC_CODE", src.getNewFacCode()));
        dest.setNewHireFacEnable(CommonCodeUtils.getName("NEW_HIRE_FAC_ENABLE", src.getNewHireFacEnable()));
        dest.setNewInstallArticleGubun(CommonCodeUtils.getName("NEW_INSTALL_ARTICLE_GUBUN", src.getNewInstallArticleGubun()));
    }
}