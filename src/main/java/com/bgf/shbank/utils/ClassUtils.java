package com.bgf.shbank.utils;

import com.bgf.shbank.core.message.CommonInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by manasobi on 2017-02-28.
 */
@Slf4j
public class ClassUtils {

    public static Class getClass(CommonInfo commonInfo, String nameUnit) {

        String className = buildClassName(commonInfo, nameUnit);
        Class decoderClass = null;

        try {
            decoderClass = org.apache.commons.lang3.ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            log.error("ClassUtils-getClass :: {}", e.getMessage());
        }

        return decoderClass;
    }

    private static String buildClassName(CommonInfo commonInfo, String className) {

        String prefixTxId = commonInfo.getMsgType();
        String suffixTxId = commonInfo.getTxType();

        String group = "";

        if (prefixTxId.startsWith("01")) {
            prefixTxId = "0100";
            group = "error";
        } else if (prefixTxId.startsWith("02")) {
            prefixTxId = "0200";
            group = "equip";
        } else if (prefixTxId.startsWith("03")) {
            prefixTxId = "0300";
            group = "cash";
        } else if (prefixTxId.startsWith("04")) {
            prefixTxId = "0400";
            group = "etc";
        } else if (prefixTxId.startsWith("05")) {
            prefixTxId = "0500";
            group = "env";
        }

        String txId = prefixTxId + suffixTxId;

        return "com.bgf.shbank.domain.mng." + group + ".sh" + txId + ".socket." + "Sh" + txId + className;
    }
}
