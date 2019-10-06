package com.fckey.bitcoin.bitflyer.apps;

import com.fckey.bitcoin.bitflyer.api.PublicApiClient;

/**
 * Created by fckey on 2016/04/20.
 */
public class BFTraderMain {

    public static void main(String[] args) {
        SampleBFExchangeMonitor monitor = new SampleBFExchangeMonitor(PublicApiClient.getInstance());
        monitor.run();

    }
}
