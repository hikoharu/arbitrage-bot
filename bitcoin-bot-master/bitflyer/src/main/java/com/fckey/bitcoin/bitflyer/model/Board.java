package com.fckey.bitcoin.bitflyer.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by fckey on 2016/04/07.
 */
public class Board {

    private double midPrice;
    private List<Order> bids;
    private List<Order> asks;

    public Board(double midPrice, List<Order> bids, List<Order> asks) {
        this.midPrice = midPrice;
        this.bids = bids;
        this.asks = asks;
    }

    public double getMidPrice() {
        return midPrice;
    }

    public List<Order> getBids() {
        return bids;
    }

    public List<Order> getAsks() {
        return asks;
    }

    @JsonCreator
    public static Board build(@JsonProperty("mid_price")double midPrice,
                              @JsonProperty("bids") List<Order> bids,
                              @JsonProperty("asks") List<Order> asks){
        return new Board(midPrice, bids, asks);
    }


    public static class Order {
        private double price;
        private  double size;

        public Order(double price, double size) {
            this.price = price;
            this.size = size;
        }

        public double getPrice() {
            return price;
        }

        public double getSize() {
            return size;
        }

        @JsonCreator
        public static Order build(@JsonProperty("price")double price,
                                  @JsonProperty("size") double size){
            return new Order(price, size);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
