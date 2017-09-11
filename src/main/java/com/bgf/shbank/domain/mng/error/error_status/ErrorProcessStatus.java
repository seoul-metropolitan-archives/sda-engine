package com.bgf.shbank.domain.mng.error.error_status;

import lombok.Getter;

/**
 * Created by tw.jang on 2017-02-16.
 */
@Getter
public enum ErrorProcessStatus {

    장애통보(0), 출동요청(1), 도착예정(2), 도착(3), 조치완료(4), 미조치(5), 출동취소(6), 자체취소(7), 상태정상(8);

    private int code;

    ErrorProcessStatus(int code) {
        this.code = code;
    }
}


