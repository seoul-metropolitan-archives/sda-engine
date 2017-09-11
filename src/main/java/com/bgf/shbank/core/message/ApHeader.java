package com.bgf.shbank.core.message;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by manasobi on 2017-01-25.
 */

public interface ApHeader {

    // 전체 Packet 크기 – 512 BYTES로 고정
    String TX_ID = "0512";

    // 0: LAST, NOT REBOUND, 1: REBOUND, 2: CONTINUE를 의미하며 당 업무는 0으로 고정.
    String REACTION_CODE = "0";

    // 'AMM' 세팅(X25 -> TCP 전환 시 소켓 송수신 전문 정당성 체크)
    String VALID_CHECK_CODE = "AMM";

    // Filler
    String FILLER = StringUtils.leftPad("", 14, " ");

}
