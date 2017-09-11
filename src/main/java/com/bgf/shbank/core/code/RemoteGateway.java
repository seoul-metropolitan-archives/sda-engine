package com.bgf.shbank.core.code;

/**
 * Created by tw.jang on 2017-02-17.
 */
public enum RemoteGateway {

    SH_BANK(0), SECURITY_CORP(1);

    int code;

    RemoteGateway(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
