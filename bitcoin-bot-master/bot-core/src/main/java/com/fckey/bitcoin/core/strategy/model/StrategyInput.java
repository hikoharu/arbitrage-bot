package com.fckey.bitcoin.core.strategy.model;

import com.fckey.bitcoin.core.common.BuySell;
import com.fckey.bitcoin.core.model.MarketPrice;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import static com.fckey.bitcoin.core.common.BuySell.BUY;
import static com.fckey.bitcoin.core.common.BuySell.SELL;

/**
 * Created by fckey on 2016/05/05.
 */
public class StrategyInput {
    public static final int DEFAULT_STD_ORDER_SIZE = 3;
    private MarketPrice marketPrice;
    private int positionPrice;
    private double positionSize;
    private BuySell buySell;
    private double stdOrderSize;

    public StrategyInput(MarketPrice marketPrice, int positionPrice, double positionSize, BuySell buySell, double stdOrderSize) {
        this.marketPrice = marketPrice;
        this.positionPrice = positionPrice;
        this.positionSize = positionSize;
        this.buySell = buySell;
        this.stdOrderSize = stdOrderSize;
    }

    public StrategyInput(MarketPrice marketPrice, int positionPrice, double positionSize, BuySell buySell) {
        this(marketPrice, positionPrice, positionSize, buySell, DEFAULT_STD_ORDER_SIZE);
    }

    public StrategyInput(MarketPrice marketPrice) {
        this(marketPrice, 0, 0, null);
    }

    public MarketPrice getMarketPrice() {
        return marketPrice;
    }

    public boolean hasPosition() {
        return positionPrice > 0;
    }

    public boolean hasLongPosition() {
        return hasPosition() && BUY.equals(getBuySell());
    }

    public boolean hasShortPosition() {
        return hasPosition() && SELL.equals(getBuySell());
    }

    public int getPositionPrice() {
        return positionPrice;
    }

    public double getPositionSize() {
        return positionSize;
    }

    public double getStdOrderSize() {
        return stdOrderSize;
    }

    public double getPositionOrStdSize() {
        return getPositionSize()>0? getPositionSize(): DEFAULT_STD_ORDER_SIZE;
    }

    public BuySell getBuySell() {
        return buySell;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

