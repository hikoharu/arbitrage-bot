package com.fckey.bitcoin.bitflyer.common;

/**
 * Created by fckey on 2016/04/14.
 */
public enum TimeInForce {
    /**
     * Gool 'Till Canceled - Default value.
     *  Valid until executed or cancelled
     */
    GTC,
    /**
     * Immediate or Cancel
     * Execute immediately all or partially when market value is good enough otherwise cancel.
     */
    IOC,
    /**
     * Fill or Kill
     * Execute all immediately or cancel
     */
    FOK
}
