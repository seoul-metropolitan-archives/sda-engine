package com.bgf.shbank.domain.mng.etc.sh04001200;

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
public class Sh04001200ModelMapper extends CustomMapper<Sh04001200, Sh04001200VO> {

    @Override
    public void mapAtoB(Sh04001200 src, Sh04001200VO dest, MappingContext context) {

        LocalDateTime txId = src.getTxId().toLocalDateTime();
        dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));

        LocalDateTime inoutDatetime = src.getInoutDatetime().toLocalDateTime();
        dest.setInoutDatetime(DateUtils.convertToString(inoutDatetime, "yyyy-MM-dd HH:mm:ss"));

        dest.setJisaCodeName(CommonCodeUtils.getName("JISA_CODE", src.getJisaCode()));
        dest.setBranchCodeName(CommonCodeUtils.getName("BRANCH_CODE", src.getBranchCode()));

        dest.setCorpCodeName(CommonCodeUtils.getName("SECURITY_CORP_CODE", src.getCorpCode()));
        dest.setInoutGubunName(CommonCodeUtils.getName("INOUT_GUBUN", src.getInoutGubun()));
        dest.setTaskGubunName(CommonCodeUtils.getName("TASK_GUBUN", src.getTaskGubun()));
    }
}
