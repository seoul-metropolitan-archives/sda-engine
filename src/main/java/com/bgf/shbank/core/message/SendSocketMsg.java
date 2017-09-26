package com.bgf.shbank.core.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by james on 2017-01-31.
 */
@Slf4j
@Data
public class SendSocketMsg {

    private CommonInfo commonInfo;

    private ByteBuf dataBytes;

    public void setCommonInfo(CommonInfo commonInfo) {
        this.commonInfo = commonInfo;
        log.debug("[SEND_MSG][COMMON_INFO] {}", commonInfo);
    }

}
