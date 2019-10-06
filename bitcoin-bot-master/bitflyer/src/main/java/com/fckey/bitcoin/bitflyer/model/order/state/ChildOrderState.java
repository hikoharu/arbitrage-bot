package com.fckey.bitcoin.bitflyer.model.order.state;

import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.State;
import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by fckey on 2016/04/16.
 */
public class ChildOrderState extends OrderState {
    public ChildOrderState(int id, String orderId, ProductCode productCode, BuySell buySell, OrderType orderType, int price, double averagePrice, double size, State state, LocalDateTime expireDate, LocalDateTime orderDate, String orderAcceptanceId, double outstandingSize, double cancelSize, double executedSize, int totalComission) {
        super(id, orderId, productCode, buySell, orderType, price, averagePrice, size, state, expireDate, orderDate, orderAcceptanceId, outstandingSize, cancelSize, executedSize, totalComission);
    }

    @JsonCreator
    public static ChildOrderState build(@JsonProperty("id") int id,
                                        @JsonProperty("child_order_id") String orderId,
                                        @JsonProperty("product_code") String productCode,
                                        @JsonProperty("side") BuySell buySell,
                                        @JsonProperty("child_order_type") String orderType,
                                        @JsonProperty("price") int price,
                                        @JsonProperty("average_price") double averagePrice,
                                        @JsonProperty("size") double size,
                                        @JsonProperty("child_order_state") State state,
                                        @JsonProperty("expire_date") String expireDate,
                                        @JsonProperty("child_order_date") String orderDate,
                                        @JsonProperty("child_order_acceptance_id") String orderAcceptanceId,
                                        @JsonProperty("outstanding_size") double outstandingSize,
                                        @JsonProperty("cancel_size") double cancelSize,
                                        @JsonProperty("executed_size") double executedSize,
                                        @JsonProperty("total_commission") int totalComission) {

        return new ChildOrderState(id, orderId,
                ProductCode.valueOf(productCode),
                buySell,
                OrderType.valueOf(orderType),
                price, averagePrice, size, state,
                LocalDateTime.parse(expireDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse(orderDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                orderAcceptanceId, outstandingSize, cancelSize, executedSize, totalComission);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
