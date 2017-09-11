package com.bgf.shbank.core.message;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by manasobi on 2017-01-25.
 */
@Slf4j
@Data
public class RecvSocketMsg {

    private String taskId;

    private CommonInfo commonInfo;

    private ByteBuf dataBytes;

    public void setCommonInfo(CommonInfo commonInfo) {
        log.debug("[RECV_MSG][COMMON_INFO] {}", commonInfo);
        this.commonInfo = parseMsgType(commonInfo);
    }

    private CommonInfo parseMsgType(CommonInfo commonInfo) {

        String msgType = commonInfo.getMsgType();

        switch (msgType) {
            case "0100": commonInfo.setResMsgType("0110"); break;
            case "0200": commonInfo.setResMsgType("0210"); break;
            case "0300": commonInfo.setResMsgType("0310"); break;
            case "0400": commonInfo.setResMsgType("0410"); break;
            case "0500": commonInfo.setResMsgType("0510"); break;
        }

        return commonInfo;
    }
}
