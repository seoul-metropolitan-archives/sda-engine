package com.bgf.shbank.domain.mng.equip.sh05001120;

import com.bgf.shbank.core.upload.AX5File;
import com.bgf.shbank.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * Created by jung-young-il on 29/03/2017.
 */
@Component
public class Sh05001120ModelMapper extends CustomMapper<Sh05001120, Sh05001120VO> {

    @Value("${onsemiro.upload.repository.img}")
    private String filePath;

    @Override
    public void mapAtoB(Sh05001120 src, Sh05001120VO dest, MappingContext context) {

        LocalDateTime overhaulDate = src.getOverhaulDate().toLocalDateTime();
        dest.setOverhaulDate(DateUtils.convertToString(overhaulDate, "yyyy-MM-dd"));

        if (src.getOutsideBillboardPhoto() != null) {
            dest.setOutsideBillboardPhotoFile(AX5File.of(filePath, src.getOutsideBillboardPhoto()));
        }

        if (src.getGatePhoto() != null) {
            dest.setGatePhotoFile(AX5File.of(filePath, src.getGatePhoto()));
        }

        if (src.getFloarPhoto() != null) {
            dest.setFloarPhotoFile(AX5File.of(filePath, src.getFloarPhoto()));
        }

        if (src.getBoothLeftPhoto() != null) {
            dest.setBoothLeftPhotoFile(AX5File.of(filePath, src.getBoothLeftPhoto()));
        }

        if (src.getBoothRightPhoto() != null) {
            dest.setBoothRightPhotoFile(AX5File.of(filePath, src.getBoothRightPhoto()));
        }

        if (src.getCeilingPhoto() != null) {
            dest.setCeilingPhotoFile(AX5File.of(filePath, src.getCeilingPhoto()));
        }

        if (src.getTerminalTopPhoto() != null) {
            dest.setTerminalTopPhotoFile(AX5File.of(filePath, src.getTerminalTopPhoto()));
        }

        if (src.getTerminalBottomPhoto() != null) {
            dest.setTerminalBottomPhotoFile(AX5File.of(filePath, src.getTerminalBottomPhoto()));
        }
    }
}
