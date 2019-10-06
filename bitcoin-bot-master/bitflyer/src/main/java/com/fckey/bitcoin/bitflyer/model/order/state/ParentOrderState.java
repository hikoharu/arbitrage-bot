package com.fckey.bitcoin.bitflyer.model.order.state;

import com.fckey.bitcoin.bitflyer.common.OrderMethod;
import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.TimeInForce;
import com.fckey.bitcoin.bitflyer.model.order.ParentOrder;
import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by fckey on 2016/04/16.
 */
public class ParentOrderState extends ParentOrder {

    private int id;
    private String orderId;
    private String orderAcceptanceId;

    public ParentOrderState(int id, String orderId, OrderMethod orderMethod, int minToExpire, TimeInForce timeInForce, List<OrderParameter> parameters, String orderAcceptanceId) {
        super(orderMethod, minToExpire, timeInForce, parameters);
        this.id = id;
        this.orderId = orderId;
        this.orderAcceptanceId = orderAcceptanceId;
    }

    public int getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderAcceptanceId() {
        return orderAcceptanceId;
    }

    @JsonCreator
    public static ParentOrderState build(
            @JsonProperty("id") int id,
            @JsonProperty("parent_order_id") String orderId,
            @JsonProperty("order_method") String orderMethod,
            @JsonProperty("minute_to_expire") int minToExpire,
            @JsonProperty("parameters") List<OrderParameter> parameters,
            @JsonProperty("parent_order_acceptance_id") String orderAcceptanceId


    ) {
        return new ParentOrderState(id, orderId, OrderMethod.valueOf(orderMethod),
                minToExpire, null, parameters, orderAcceptanceId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class OrderParameterState extends OrderParameter {
        public OrderParameterState(ProductCode productCode, OrderType orderType, BuySell buySell, int price, double size, int trigerPrice, int offset) {
            super(productCode, orderType, buySell, price, size, trigerPrice, offset);
        }

        @JsonCreator
        public static OrderParameterState build(@JsonProperty("product_code") String productCode,
                                                @JsonProperty("condition_type") String orderType,
                                                @JsonProperty("side") String buySell,
                                                @JsonProperty("price") int price,
                                                @JsonProperty("size") double size,
                                                @JsonProperty("target_price") int trigerPrice,
                                                @JsonProperty("offset") int offset) {
            return new OrderParameterState(ProductCode.valueOf(productCode),
                    OrderType.valueOf(orderType),
                    BuySell.valueOf(buySell),
                    price, size, trigerPrice, offset);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
