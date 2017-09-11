package com.bgf.shbank.domain.mng.equip.facility_status;

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
public class FacilityStatusModelMapper extends CustomMapper<FacilityStatus, FacilityStatusVO> {

    @Override
    public void mapAtoB(FacilityStatus src, FacilityStatusVO dest, MappingContext context) {

        if (src.getAdoptDate() != null) {
            LocalDateTime adoptDate = src.getAdoptDate().toLocalDateTime();
            dest.setAdoptDate(DateUtils.convertToString(adoptDate, "yyyy-MM-dd HH:mm:ss"));
        }

        if (src.getInstallDate() != null) {
            LocalDateTime installDate = src.getInstallDate().toLocalDateTime();
            dest.setInstallDate(DateUtils.convertToString(installDate, "yyyy-MM-dd HH:mm:ss"));
        }

        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));

        dest.setFacGubunCodeName(CommonCodeUtils.getName("FAC_GUBUN_CODE", src.getFacGubunCode()));
        dest.setFacCodeName(CommonCodeUtils.getName("FAC_CODE", src.getFacCode()));

        String operEnable = src.getOperEnable();
        if (operEnable != null && !operEnable.isEmpty()) {
            dest.setOperEnableName(CommonCodeUtils.getName("OPER_ENABLE", src.getOperEnable()));
        }

        dest.setHireFacEnableName(CommonCodeUtils.getName("HIRE_FAC_ENABLE", src.getHireFacEnable()));
        dest.setInstallArticleGubunName(CommonCodeUtils.getName("INSTALL_ARTICLE_GUBUN", src.getInstallArticleGubun()));


    }


}