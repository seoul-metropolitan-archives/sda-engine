package com.bgf.shbank.domain.mng.equip.sh02001190;

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
public class Sh02001190ModelMapper extends CustomMapper<Sh02001190, Sh02001190VO> {

    @Override
    public void mapAtoB(Sh02001190 src, Sh02001190VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

        dest.setStextGubun(CommonCodeUtils.getName("EQUIP_STEXT_GUBUN", src.getStextGubun()));
        dest.setChangeBeforeFacGubunCodeName(CommonCodeUtils.getName("FAC_GUBUN_CODE", src.getChangeBeforeFacGubunCode()));
        dest.setChangeBeforeFacCodeName(CommonCodeUtils.getName("FAC_CODE", src.getChangeBeforeFacCode()));

        dest.setChangeAfterFacGubunCodeName(CommonCodeUtils.getName("FAC_GUBUN_CODE", src.getChangeAfterFacGubunCode()));
        //dest.setChangeAfterFacCodeName(CommonCodeUtils.getName("FAC_CODE", src.getChangeAfterFacCode()));
    }
}
