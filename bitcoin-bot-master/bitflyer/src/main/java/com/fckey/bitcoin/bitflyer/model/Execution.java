package com.fckey.bitcoin.bitflyer.model;

import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by fckey on 2016/04/02.
 */
public class Execution {
    private int id;
    private BuySell buySell;
    private int price;
    private double size;
    private LocalDateTime execDate;
    private String buyChildOrderAcceptanceId;
    private String sellChildOrderAcceptanceId;

//    public Execution() {
//        //Required for Jackson
//    }


    public Execution(int id, BuySell buySell, int price, double size, LocalDateTime execDate, String buyChildOrderAcceptanceId, String sellChildOrderAcceptanceId) {
        this.id = id;
        this.buySell = buySell;
        this.price = price;
        this.size = size;
        this.execDate = execDate;
        this.buyChildOrderAcceptanceId = buyChildOrderAcceptanceId;
        this.sellChildOrderAcceptanceId = sellChildOrderAcceptanceId;
    }

    @JsonCreator
    public static Execution build(@JsonProperty("id")int id,
                     @JsonProperty("side")String buySell,
                     @JsonProperty("price") int price,
                     @JsonProperty("size") double size,
                     @JsonProperty("exec_date") String execDate,
                     @JsonProperty("buy_child_order_acceptance_id") String buyChildOrderAcceptanceId,
                     @JsonProperty("sell_child_order_acceptance_id") String sellChildOrderAcceptanceId) {
        return new Execution(id,
                BuySell.valueOf(buySell),
                price, size,
                LocalDateTime.parse(execDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                buyChildOrderAcceptanceId,
                sellChildOrderAcceptanceId);
    }

    public int getId() {
        return id;
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

    public LocalDateTime getExecDate() {
        return execDate;
    }

    public String getBuyChildOrderAcceptanceId() {
        return buyChildOrderAcceptanceId;
    }

    public String getSellChildOrderAcceptanceId() {
        return sellChildOrderAcceptanceId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
