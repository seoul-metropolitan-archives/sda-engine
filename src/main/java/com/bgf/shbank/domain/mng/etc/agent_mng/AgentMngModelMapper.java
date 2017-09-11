package com.bgf.shbank.domain.mng.etc.agent_mng;

import com.bgf.shbank.core.upload.AX5File;
import io.onsemiro.utils.DateUtils;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * Created by tw.jang on 2017-02-09.
 */
@Component
public class AgentMngModelMapper extends CustomMapper<AgentMng, AgentMngVO> {

    @Value("${onsemiro.upload.repository.img}")
    private String filePath;

    @Override
    public void mapAtoB(AgentMng src, AgentMngVO dest, MappingContext context) {
        if(src.getTxId() != null) {
            LocalDateTime txId = src.getTxId().toLocalDateTime();
            dest.setTxId(DateUtils.convertToString(txId, "yyyy-MM-dd HH:mm:ss"));
        }

        if (!StringUtils.isEmpty(src.getDigitalSealUrl())) {
            dest.setDigitalSealUrlFile(AX5File.of(filePath, src.getDigitalSealUrl()));
        }

        if (!StringUtils.isEmpty(src.getDigitalSignUrl())) {
            dest.setDigitalSignUrlFile(AX5File.of(filePath, src.getDigitalSignUrl()));
        }
    }
}

