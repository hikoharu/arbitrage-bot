package com.fckey.bitcoin.core.strategy.model;

import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by fckey on 2016/04/24.
 */
public class OrderSuggestion {
    public static final int DEFAULT_STD_ORDER_SIZE = 3;

    private String tag;
    private int price;
    private BuySell buySell;
    private double quantity;
    private int priority;

    public OrderSuggestion(String tag, int price, BuySell buySell, double quantity, int priority) {
        this.tag = tag;
        this.price = price;
        this.buySell = buySell;
        this.quantity = quantity;
        this.priority = priority;
    }

    public OrderSuggestion(String tag, int price, BuySell buySell, double quantity) {
        this(tag, price, buySell, quantity, 3);
    }

    public OrderSuggestion(int price, BuySell buySell, double quantity) {
        this("", price, buySell, quantity, 3);
    }

    public String getTag() {
        return tag;
    }

    public int getPrice() {
        return price;
    }

    public BuySell getBuySell() {
        return buySell;
    }

    public double getQuantity() {
        return quantity;
    }


    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
