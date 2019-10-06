package com.fckey.bitcoin.bitflyer.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/07.
 */
public  class Health {

    private ExchangeStatus status;

    public Health(ExchangeStatus status) {
        this.status = status;
    }

    @JsonCreator
    public static Health build(@JsonProperty("status")String status){
        ExchangeStatus currentStatus = null;
        for (ExchangeStatus s: ExchangeStatus.values()) {
            if (s.getValue().equals(status)) {
                currentStatus = s;
                break;
            }
        }
        if (currentStatus == null) {
            throw new IllegalStateException("Received status is not expected");
        }
        return new Health(currentStatus);
    }

    public ExchangeStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    public static enum ExchangeStatus {
        NORMAL("NORMAL"),
        BUSY("BUSY"),
        VERY_BUSY("VERY BUSY"),
        STOP("STOP");

        ExchangeStatus(String value) {
            this.value = value;
        }

        String value;

        public String getValue() {
            return value;
        }
    }
}
