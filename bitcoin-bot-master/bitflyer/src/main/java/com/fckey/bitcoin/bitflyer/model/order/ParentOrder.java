package com.fckey.bitcoin.bitflyer.model.order;

import com.fckey.bitcoin.bitflyer.common.OrderMethod;
import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.TimeInForce;
import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by fckey on 2016/04/14.
 */
public class ParentOrder {

    @JsonProperty("order_method")
    private OrderMethod orderMethod;

    @JsonProperty("minute_to_expire")
    private int minToExpire;

    @JsonProperty("time_in_force")
    private TimeInForce timeInForce;
    private List<OrderParameter> parameters;

    public ParentOrder(OrderMethod orderMethod, int minToExpire, TimeInForce timeInForce, List<OrderParameter> parameters) {
        this.orderMethod = orderMethod;
        this.minToExpire = minToExpire;
        this.timeInForce = timeInForce;
        this.parameters = parameters;
    }

    public OrderMethod getOrderMethod() {
        return orderMethod;
    }

    public int getMinToExpire() {
        return minToExpire;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    public List<OrderParameter> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class OrderParameter extends OrderBase {
        @JsonProperty("trigger_price")
        private int trigerPrice;
        private int offset;

        public OrderParameter(ProductCode productCode, OrderType orderType, BuySell buySell, int price, double size) {
            super(productCode, orderType, buySell, price, size);
            verifyPrice(orderType, price);
        }

        /**
         * @param productCode
         * @param orderType
         * @param buySell
         * @param price
         * @param size
         * @param trigerPrice
         */
        public OrderParameter(ProductCode productCode, OrderType orderType, BuySell buySell, int price, double size, int trigerPrice) {
            super(productCode, orderType, buySell, price, size);
            verifyTrigerPrice(orderType, trigerPrice);
            this.trigerPrice = trigerPrice;
        }

        /**
         * @param productCode
         * @param orderType
         * @param buySell
         * @param price
         * @param offset
         * @param size
         */
        public OrderParameter(ProductCode productCode, OrderType orderType, BuySell buySell, int price, int offset, double size) {
            super(productCode, orderType, buySell, price, size);
            verifyTrigerPrice(orderType, trigerPrice);
            this.trigerPrice = trigerPrice;
        }

        /**
         * @param productCode
         * @param orderType
         * @param buySell
         * @param price
         * @param size
         * @param trigerPrice
         * @param offset
         */
        public OrderParameter(ProductCode productCode, OrderType orderType, BuySell buySell, int price, double size, int trigerPrice, int offset) {
            super(productCode, orderType, buySell, price, size);
            verifyPrice(orderType, price);
            verifyTrigerPrice(orderType, trigerPrice);
            verifyOffset(orderType, offset);
            this.trigerPrice = trigerPrice;
            this.offset = offset;
        }

        void verifyOffset(OrderType orderType, int offset) {
            if (orderType.equals(OrderType.TRAIL) && offset <= 0) {
                throw new IllegalStateException("Offset need to be bigger than 0 when order type TRAIL");
            }
        }

        void verifyTrigerPrice(OrderType orderType, int trigerPrice) {
            if (orderType.equals(OrderType.STOP) || orderType.equals(OrderType.STOP_LIMIT) &&
                    trigerPrice == 0) {
                throw new IllegalStateException("TrigerPrice need to be specified with order type STOP or STOP_LIMIT");

            }
        }

        void verifyPrice(OrderType orderType, int price) {
            if ((orderType.equals(OrderType.LIMIT) || orderType.equals(OrderType.STOP_LIMIT)) &&
                    price == 0) {
                throw new IllegalStateException("Price need to be specified with order type LIMIT or STOP_LIMIT");
            }
        }

        @Override
        @JsonProperty("condition_type")
        public OrderType getOrderType() {
            return orderType;
        }

        public int getTrigerPrice() {
            return trigerPrice;
        }

        public int getOffset() {
            return offset;
        }

        @JsonCreator
        public static OrderParameter build(@JsonProperty("product_code") String productCode,
                                           @JsonProperty("condition_type") String orderType,
                                           @JsonProperty("side") String buySell,
                                           @JsonProperty("price") int price,
                                           @JsonProperty("size") double size,
                                           @JsonProperty("trigger_price") int trigerPrice,
                                           @JsonProperty("offset") int offset) {

            return new OrderParameter(ProductCode.valueOf(productCode),
                    OrderType.valueOf(orderType),
                    BuySell.valueOf(buySell),
                    price, size, trigerPrice, offset);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
