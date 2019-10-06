package com.fckey.bitcoin.bitflyer.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/13.
 */
public class Collateral {
    
    private int collateral;
    private int requireCollateral;
    private int pnl;
    private double keepRate;

    public Collateral(int collateral, int requireCollateral, int pnl, double keepRate) {
        this.collateral = collateral;
        this.requireCollateral = requireCollateral;
        this.pnl = pnl;
        this.keepRate = keepRate;
    }

    public int getCollateral() {
        return collateral;
    }

    public int getRequireCollateral() {
        return requireCollateral;
    }

    public int getPnl() {
        return pnl;
    }

    public double getKeepRate() {
        return keepRate;
    }

    @JsonCreator
    public static Collateral build(@JsonProperty("collateral")int collateral,
                              @JsonProperty("require_collateral") int requireCollateral,
                              @JsonProperty("open_position_pnl") int pnl,
                              @JsonProperty("keep_rate") double keepRate){
        return new Collateral(collateral, requireCollateral, pnl, keepRate);
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
