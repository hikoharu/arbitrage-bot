package com.fckey.bitcoin.core.api;

/**
 * Created by fckey on 2016/05/15.
 */
public class AccessParams {
    private String accessKey;
    private String accessSecret;

    public AccessParams(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }
}
