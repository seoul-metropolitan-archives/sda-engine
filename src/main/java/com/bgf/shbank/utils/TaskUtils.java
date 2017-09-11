package com.bgf.shbank.utils;

import com.bgf.shbank.core.code.RemoteGateway;
import com.bgf.shbank.core.message.MsgSeqHandler;
import com.google.common.collect.Maps;
import io.onsemiro.core.context.AppContextManager;
import io.onsemiro.core.domain.code.CommonCode;
import io.onsemiro.utils.CommonCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by tw.jang on 2017-02-23.
 */
@Slf4j
public class TaskUtils {

    private static Map<String, String> jisaCodeMap;

    public static String getJisaCode(String branchCode) {

        if (jisaCodeMap == null) {
            jisaCodeMap = buildJisaCodeMap();
        }

        String jisaCode = jisaCodeMap.get(branchCode);

        if (StringUtils.isEmpty(jisaCode)) {
            jisaCodeMap = buildJisaCodeMap();
            jisaCode = jisaCodeMap.get(branchCode);
        }

        if (StringUtils.isEmpty(jisaCode)) {
            jisaCode = "99";
        }

        return jisaCode;
    }

    private static Map<String, String> buildJisaCodeMap() {

        List<CommonCode> commonCodes = CommonCodeUtils.get("JISA_BRANCH_CODE");

        Map<String, String> jisaCodeMap = Maps.newHashMap();

        commonCodes.forEach(commonCode -> {

            String jisaBranchCode = commonCode.getCode();
            String jisaCode = jisaBranchCode.substring(0, 2);
            String branchCode = jisaBranchCode.substring(2);

            jisaCodeMap.put(branchCode, jisaCode);
        });

        return jisaCodeMap;
    }

    public static void waitSeconds(int seconds) {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("TaskUtils-waitSeconds :: {}", e.getMessage());
        }
    }

    public static Timestamp newTxID() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static String findCooperationSn(RemoteGateway remoteGateway) {

        MsgSeqHandler msgSeqHandler =  AppContextManager.getBean(MsgSeqHandler.class);

        return msgSeqHandler.findMsgSeq(remoteGateway);
    }

    public static String findSTextType(String txId) {

        String type;

        switch (txId) {
            case "03001140":
            case "03001150":
            case "03001160":
            case "03001170":
            case "03001180":
            case "03001190":
            case "04001110":
            case "04001130":
            case "04001140":
            case "04001150":
            case "05001110":
            case "05001120":
            case "05001130":
            case "05001140":
                type = "1"; break;
            default:
                type = "0";
        }

        return type;
    }

}
