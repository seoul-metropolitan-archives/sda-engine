package com.bgf.shbank.domain.mng.cash.sh03001220;

import com.bgf.shbank.core.upload.AX5File;
import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by jung-young-il on 16/02/2017.
 */
@Component
public class Sh03001220ModelMapper extends CustomMapper<Sh03001220, Sh03001220VO> {

    @Value("${onsemiro.upload.repository.img}")
    private String filePath;

    @Override
    public void mapAtoB(Sh03001220 src, Sh03001220VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime reqDate = src.getReqDate().toLocalDateTime();
        dest.setReqDate(DateUtils.convertToString(reqDate, "yyyy-MM-dd"));

        if (src.getChargeEmpRegno() != null) {
            dest.setChargeEmpRegno(src.getChargeEmpRegno().substring(0,6));
        }

        if (src.getDigitalSignUrl() != null) {
            String getDigitalSignId = src.getDigitalSignUrl().split("/")[src.getDigitalSignUrl().split("/").length-1];
            String Id1 = getDigitalSignId.substring(0,getDigitalSignId.lastIndexOf("."));
            dest.setDigitalSignUrlFile(AX5File.of(filePath, Id1));
        }

        if (src.getChargeEmpPhotoUrl() != null) {
            String getChargeEmpPhotoId = src.getChargeEmpPhotoUrl().split("/")[src.getChargeEmpPhotoUrl().split("/").length-1];
            String Id2 = getChargeEmpPhotoId.substring(0,getChargeEmpPhotoId.lastIndexOf("."));
            dest.setChargeEmpPhotoUrlFile(AX5File.of(filePath, Id2));
        }
    }
}