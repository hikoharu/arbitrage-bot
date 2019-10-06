package com.fckey.bitcoin.bitflyer.model.order.state;

import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.State;
import com.fckey.bitcoin.bitflyer.model.order.OrderBase;
import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * Created by fckey on 2016/04/16.
 */
public class OrderState extends OrderBase {
    private int id;
    private String orderId;
    private double averagePrice;
    private State state;
    private LocalDateTime expireDate;
    private LocalDateTime orderDate;
    private String orderAcceptanceId;
    private double outstandingSize;
    private double cancelSize;
    private double executedSize;
    private int totalComission;

    public OrderState(int id, String orderId, ProductCode productCode, BuySell buySell, OrderType orderType, int price, double averagePrice, double size, State state, LocalDateTime expireDate, LocalDateTime orderDate, String orderAcceptanceId, double outstandingSize, double cancelSize, double executedSize, int totalComission) {
        super(productCode, orderType, buySell, price, size);
        this.id = id;
        this.orderId = orderId;
        this.averagePrice = averagePrice;
        this.state = state;
        this.expireDate = expireDate;
        this.orderDate = orderDate;
        this.orderAcceptanceId = orderAcceptanceId;
        this.outstandingSize = outstandingSize;
        this.cancelSize = cancelSize;
        this.executedSize = executedSize;
        this.totalComission = totalComission;
    }

    public int getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public State getState() {
        return state;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getOrderAcceptanceId() {
        return orderAcceptanceId;
    }

    public double getOutstandingSize() {
        return outstandingSize;
    }

    public double getCancelSize() {
        return cancelSize;
    }

    public double getExecutedSize() {
        return executedSize;
    }

    public int getTotalComission() {
        return totalComission;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
