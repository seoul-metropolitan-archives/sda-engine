package com.bgf.shbank.domain.mng.etc.sh04001180;

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
public class Sh04001180ModelMapper extends CustomMapper<Sh04001180, Sh04001180VO> {

    @Override
    public void mapAtoB(Sh04001180 src, Sh04001180VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

        dest.setBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));
        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));

    }
}
