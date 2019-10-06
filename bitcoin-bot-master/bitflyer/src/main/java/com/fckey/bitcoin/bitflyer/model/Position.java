package com.fckey.bitcoin.bitflyer.model;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by fckey on 2016/04/19.
 * Position of open interest
 */
public class Position {
    @JsonProperty("product_code")
    private ProductCode productCode;
    @JsonProperty("side")
    private BuySell buySell;
    private int price;
    private double size;
    private double commission;
    private int swapPointAccumlate;
    private int requireCollateral;
    private LocalDateTime openDate;
    private int leverage;
    private int pnl;

    public Position(ProductCode productCode, BuySell buySell, int price, double size, double commission, int swapPointAccumlate, int requireCollateral, LocalDateTime openDate, int leverage, int pnl) {
        this.productCode = productCode;
        this.buySell = buySell;
        this.price = price;
        this.size = size;
        this.commission = commission;
        this.swapPointAccumlate = swapPointAccumlate;
        this.requireCollateral = requireCollateral;
        this.openDate = openDate;
        this.leverage = leverage;
        this.pnl = pnl;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public BuySell getBuySell() {
        return buySell;
    }

    public int getPrice() {
        return price;
    }

    public double getSize() {
        return size;
    }

    public double getCommission() {
        return commission;
    }

    public int getSwapPointAccumlate() {
        return swapPointAccumlate;
    }

    public int getRequireCollateral() {
        return requireCollateral;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public int getLeverage() {
        return leverage;
    }

    public int getPnl() {
        return pnl;
    }

    @JsonCreator
    public static Position build(@JsonProperty("product_code") String productCode,
                                 @JsonProperty("side") BuySell buySell,
                                 @JsonProperty("price") int price,
                                 @JsonProperty("size") double size,
                                 @JsonProperty("commission") double commission,
                                 @JsonProperty("swap_point_accumulate") int swapPointAccumulate,
                                 @JsonProperty("require_collateral") int requireCollateral,
                                 @JsonProperty("open_date") String openDate,
                                 @JsonProperty("leverage") int leverage,
                                 @JsonProperty("open_position_pnl") int pnl) {

        return new Position(ProductCode.valueOf(productCode),
                buySell, price, size, commission,
                swapPointAccumulate, requireCollateral,
                LocalDateTime.parse(openDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                leverage, pnl);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
