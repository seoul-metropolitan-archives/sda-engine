package com.bgf.shbank.domain.mng.error.error_status;

import lombok.Getter;

/**
 * Created by james on 2017-02-16.
 */
@Getter
public enum StextSendGubun {

    미전송("0"), 전문전송("1"), 응답완료("2"), 전송실패("3");

    private String code;

    StextSendGubun(String code) {
        this.code = code;
    }
}


