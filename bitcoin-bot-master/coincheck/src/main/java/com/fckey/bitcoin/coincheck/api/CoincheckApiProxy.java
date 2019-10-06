package com.fckey.bitcoin.coincheck.api;

import com.fckey.bitcoin.coincheck.api.header.CoincheckRequestHeaderFactory;
import com.fckey.bitcoin.coincheck.api.header.CoincheckRequestHeaderFactory.CoincheckRequestHeaderElement;
import com.fckey.bitcoin.core.api.ApiClientBase;
import com.fckey.bitcoin.core.api.header.RequestHeaderFactory;

/**
 * Created by fckey on 2016/04/04.
 */
public class CoincheckApiProxy extends ApiClientBase {


    public static final String COINCHECK_API_URL = "https://coincheck.jp/api/";
    private String baseUrl;

    RequestHeaderFactory headerFactory;

    public static void main(String[] args) {
        String key = "API_KEY";
        String secret = "API_SECRET";
        CoincheckApiProxy api = new CoincheckApiProxy(key, secret);
        System.out.println(api.getTicker());
//        System.out.println(api.getBalance());
    }

    public CoincheckApiProxy(String apiKey, String apiSecret) {
        this.baseUrl = COINCHECK_API_URL;
        headerFactory = CoincheckRequestHeaderFactory.getInstance();
    }

    public String getTicker() {
        String url = baseUrl+"ticker";
        String jsonString = requestGetByUrlSimple(url);
        return jsonString;
    }

    public String getBalance() {
        String url = baseUrl + "accounts/balance";
        String jsonString = requestGetByUrlWithHeader(url, headerFactory.setElement(new CoincheckRequestHeaderElement(url)).build());
        return jsonString;
    }

}
