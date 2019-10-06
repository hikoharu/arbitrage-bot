package com.fckey.bitcoin.core.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * Created by fckey on 2016/04/29.
 */
public class MarketPrice {
    private String tag;
    private int ask;
    private int bid;
    private int bestAsk;
    private double bestAskSize;
    private int bestBid;
    private double bestBidSize;
    private double spread;
    private LocalDateTime time;


    /**
     *
     * @param tag
     * @param ask
     * @param bid
     * @param bestAsk
     * @param bestAskSize
     * @param bestBid
     * @param bestBidSize
     * @param spread
     * @param time
     */
    public MarketPrice(String tag, int ask, int bid, int bestAsk, double bestAskSize, int bestBid, double bestBidSize, double spread, LocalDateTime time) {
        this.tag = tag;
        this.ask = ask;
        this.bid = bid;
        this.bestAsk = bestAsk;
        this.bestAskSize = bestAskSize;
        this.bestBid = bestBid;
        this.bestBidSize = bestBidSize;
        this.spread = spread;
        this.time = time;
    }

    public MarketPrice(int ask, int bid, int bestAsk, double bestAskSize, int bestBid, double bestBidSize) {
        this("", ask, bid, bestAsk, bestAskSize, bestBid, bestBidSize, (ask/bid)*100, LocalDateTime.now());
    }

    /**
     *
     * @param tag
     * @param spread
     * @param time
     */
    public MarketPrice(String tag, int ask, int bid, double spread, LocalDateTime time) {
        this("", ask, bid, ask, 0, bid, 0, spread, LocalDateTime.now());

    }

    public MarketPrice(String tag, int ask, int bid, LocalDateTime time) {
        this(tag, ask, bid, ask, 0, bid, 0, (ask/bid)*100, time);

    }

    /**
     *
     * @param tag
     * @param spread
     */
    public MarketPrice(String tag, int ask, int bid, double spread) {
        this(tag, ask, bid, spread, LocalDateTime.now());
    }

    /**
     *
     * @param ask
     * @param bid
     */
    public MarketPrice(int ask, int bid) {
        this("", ask, bid, (ask/bid)*100, LocalDateTime.now());
    }

    public MarketPrice( double spread) {
        this("", 0, 0, spread, LocalDateTime.now());
    }

    public String getTag() {
        return tag;
    }

    public int getAsk() {
        return ask;
    }

    public int getBid() {
        return bid;
    }

    public double getSpread() {
        return spread;
    }

    public int getPriceAsInt() {
        return (int) spread;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getBestBid() {
        return bestBid;
    }

    public double getBestBidSize() {
        return bestBidSize;
    }

    public int getBestAsk() {
        return bestAsk;
    }

    public double getBestAskSize() {
        return bestAskSize;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
