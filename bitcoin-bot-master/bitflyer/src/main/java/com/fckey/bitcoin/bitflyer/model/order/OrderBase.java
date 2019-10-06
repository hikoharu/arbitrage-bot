package com.fckey.bitcoin.bitflyer.model.order;

import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.core.common.BuySell;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/14.
 */
public class OrderBase {

    @JsonProperty("product_code")
    private ProductCode productCode;
    protected OrderType orderType;
    @JsonProperty("side")
    private BuySell buySell;
    private double price;
    private double size;

    public OrderBase(ProductCode productCode, OrderType orderType, BuySell buySell, double price, double size) {
        this.productCode = productCode;
        this.orderType = orderType;
        this.buySell = buySell;
        this.price = price;
        this.size = size;
    }

    public OrderBase(ProductCode productCode, OrderType orderType, BuySell buySell, double size) {
        this.productCode = productCode;
        this.orderType = orderType;
        this.buySell = buySell;
        this.size = size;
    }

    public ProductCode getProductCode() {
        return productCode;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public BuySell getBuySell() {
        return buySell;
    }

    public double getPrice() {
        return price;
    }

    public double getSize() {
        return size;
    }
}
