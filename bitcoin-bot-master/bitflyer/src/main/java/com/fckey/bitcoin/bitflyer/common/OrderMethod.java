package com.fckey.bitcoin.bitflyer.common;

/**
 * Created by fckey on 2016/04/14.
 */
public enum OrderMethod {
    /**
     * Single order.
     */
    SIMPLE,
    /**
     * Make two orders. If first order is executed, second one is ordered automatically
     */
    IFD,
    /**
     * Make two orders. If one of the orders is executed, another will be cancelled.
     */
    OCO,
    /**
     * If first order is executed, OCO will be ordered automatically.
     */
    IFDOCO
}
