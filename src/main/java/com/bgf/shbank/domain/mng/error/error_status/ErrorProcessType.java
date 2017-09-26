package com.bgf.shbank.domain.mng.error.error_status;

import lombok.Getter;

/**
 * Created by james on 2017-02-16.
 */
@Getter
public enum ErrorProcessType {

    장애1차("1"), 장애2차("2"), 현금부족예보("3"), 현금부족("4"), 자체출동("5"), 민원점검("6");

    private String code;

    ErrorProcessType(String code) {
        this.code = code;
    }
}


