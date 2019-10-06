package com.fckey.bitcoin.bitflyer.model.order;

import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.TimeInForce;
import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/14.
 */
public class ChildOrder extends OrderBase {

    @JsonProperty("minute_to_expire")
    private int minToExpire;

    @JsonProperty("time_in_force")
    private TimeInForce timeInForce;

    /**
     * Used for orderType LIMIT
     * @param productCode
     * @param orderType
     * @param buySell
     * @param price
     * @param size
     * @param minToExpire
     * @param timeInForce
     */
    public ChildOrder(ProductCode productCode, OrderType orderType, BuySell buySell, double price, double size, int minToExpire, TimeInForce timeInForce) {
        super(productCode, orderType, buySell, price, size);
        this.minToExpire = minToExpire;
        this.timeInForce = timeInForce;
    }

    public ChildOrder(ProductCode productCode, OrderType orderType, BuySell buySell, double size, int minToExpire, TimeInForce timeInForce) {
        super(productCode, orderType, buySell,size);
        this.minToExpire = minToExpire;
        this.timeInForce = timeInForce;
    }

    public int getMinToExpire() {
        return minToExpire;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    @JsonProperty("child_order_type")
    @Override
    public OrderType getOrderType() {
        return orderType;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
