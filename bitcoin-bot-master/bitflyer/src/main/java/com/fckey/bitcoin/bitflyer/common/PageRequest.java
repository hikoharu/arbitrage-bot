package com.fckey.bitcoin.bitflyer.common;

/**
 * Created by fckey on 2016/04/07.
 */
public enum PageRequest {
    COUNT("count"),
    BEFORE("before"),
    AFTER("after");

    PageRequest(String value) {
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }
}
