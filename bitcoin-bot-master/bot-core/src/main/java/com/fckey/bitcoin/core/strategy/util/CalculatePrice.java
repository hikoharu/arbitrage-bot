package com.fckey.bitcoin.core.strategy.util;

/**
 * Created by fckey on 2016/05/06.
 */
public class CalculatePrice {

    public static int calcBidPrice(int bid, double bidSize) {
        if (bidSize < 5) {
            return bid;
        }
        return bid + 1;
    }

    public static int calcAskPrice(int ask, double askSize) {
        if (askSize < 5) {
            return ask;
        }
        return ask - 1;
    }

}
