package com.fckey.bitcoin.bitflyer.model;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by fckey on 2016/04/07.
 */
public class Ticker {

    private ProductCode productCode;
    private LocalDateTime timestamp;
    private int tickId;
    private int bestBid;
    private double bestBidSize;
    private double totalBidDepth;
    private int bestAsk;
    private double bestAskSize;
    private double totalAskDepth;
    private int ltp;
    private double volume;
    private double volumeByProduct;

    public Ticker(ProductCode productCode, LocalDateTime timestamp, int tickId,
                  int bestBid, double bestBidSize, double totalBidDepth, int bestAsk,
                  double bestAskSize, double totalAskDepth, int ltp, double volume,
                  double volumeByProduct) {
        this.productCode = productCode;
        this.timestamp = timestamp;
        this.tickId = tickId;
        this.bestBid = bestBid;
        this.bestBidSize = bestBidSize;
        this.totalBidDepth = totalBidDepth;
        this.bestAsk = bestAsk;
        this.bestAskSize = bestAskSize;
        this.totalAskDepth = totalAskDepth;
        this.ltp = ltp;
        this.volume = volume;
        this.volumeByProduct = volumeByProduct;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getTickId() {
        return tickId;
    }

    public int getBestBid() {
        return bestBid;
    }

    public double getBestBidSize() {
        return bestBidSize;
    }

    public double getTotalBidDepth() {
        return totalBidDepth;
    }

    public int getBestAsk() {
        return bestAsk;
    }

    public double getBestAskSize() {
        return bestAskSize;
    }

    public double getTotalAskDepth() {
        return totalAskDepth;
    }

    public int getLtp() {
        return ltp;
    }

    public double getVolume() {
        return volume;
    }

    public double getVolumeByProduct() {
        return volumeByProduct;
    }

    @JsonCreator
    public static Ticker build(
            @JsonProperty("product_code") String productCode,
            @JsonProperty("timestamp") String timestamp,
            @JsonProperty("tick_id") int tickId,
            @JsonProperty("best_bid") int bestBid,
            @JsonProperty("best_bid_size") double bestBidSize,
            @JsonProperty("total_bid_depth") double totalBidDepth,
            @JsonProperty("best_ask") int bestAsk,
            @JsonProperty("best_ask_size") double bestAskSize,
            @JsonProperty("total_ask_depth") double totalAskDepth,
            @JsonProperty("ltp") int ltp,
            @JsonProperty("volume") double volume,
            @JsonProperty("volume_by_product") double volumeByProduct) {

        return new Ticker(ProductCode.valueOf(productCode),
                LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                tickId,
                bestBid,
                bestBidSize,
                totalBidDepth,
                bestAsk,
                bestAskSize,
                totalAskDepth,
                ltp,
                volume,
                volumeByProduct);
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
