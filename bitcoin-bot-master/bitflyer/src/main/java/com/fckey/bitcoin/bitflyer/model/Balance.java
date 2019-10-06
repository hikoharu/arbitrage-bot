package com.fckey.bitcoin.bitflyer.model;

import com.fckey.bitcoin.bitflyer.common.Currency;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/12.
 */
public class Balance {

    private String currency;
    private double amount;
    private double available;

    public Balance(String currency, double amount, double available) {
        this.currency = currency;
        this.amount = amount;
        this.available = available;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public double getAvailable() {
        return available;
    }

    @JsonCreator
    public static Balance build(@JsonProperty("currency_code")String curr,
                              @JsonProperty("amount") double amount,
                                @JsonProperty("available") double available){
        return new Balance(curr, amount, available);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
