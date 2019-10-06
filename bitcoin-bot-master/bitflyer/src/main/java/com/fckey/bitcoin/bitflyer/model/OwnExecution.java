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
public class OwnExecution {
    private int id;
    private String childOrderId;
    private BuySell buySell;
    private double size;
    private int price;
    private LocalDateTime execDate;
    private int commission;
    private String childOrderAcceptanceId;

    public OwnExecution(int id, String childOrderId, BuySell buySell, double size, int price, LocalDateTime execDate, int commission, String childOrderAcceptanceId) {
        this.id = id;
        this.childOrderId = childOrderId;
        this.buySell = buySell;
        this.size = size;
        this.price = price;
        this.execDate = execDate;
        this.commission = commission;
        this.childOrderAcceptanceId = childOrderAcceptanceId;
    }

    @JsonCreator
    public static OwnExecution build(@JsonProperty("id") int id,
                                     @JsonProperty("child_order_id") String childOrderId,
                                     @JsonProperty("side") String buySell,
                                     @JsonProperty("size") double size,
                                     @JsonProperty("price") int price,
                                     @JsonProperty("exec_date") String execDate,
                                     @JsonProperty("commission") int commission,
                                     @JsonProperty("child_order_acceptance_id") String childOrderAcceptanceId) {
        return new OwnExecution(id,
                childOrderId,
                BuySell.valueOf(buySell),
                size, price,
                LocalDateTime.parse(execDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                commission,
                childOrderAcceptanceId);
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


    public String getChildOrderId() {
        return childOrderId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
