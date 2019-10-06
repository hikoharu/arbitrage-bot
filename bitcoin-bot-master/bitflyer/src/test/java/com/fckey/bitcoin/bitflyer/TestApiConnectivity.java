package com.fckey.bitcoin.bitflyer;

import com.fckey.bitcoin.bitflyer.api.PublicApiClient;

import java.io.IOException;

/**
 * Created by fckey on 2016/04/05.
 * This is not unit test. Actually run the Api call.
 */
public class TestApiConnectivity {
    private static PublicApiClient pApi = PublicApiClient.getInstance();

    public static void main(String[] args) throws IOException {
        System.out.println(pApi.getTicker());;
    }
}
