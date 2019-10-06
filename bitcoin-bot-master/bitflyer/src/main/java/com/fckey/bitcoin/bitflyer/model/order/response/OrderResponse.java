package com.fckey.bitcoin.bitflyer.model.order.response;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/15.
 */
public class OrderResponse {

    protected String responseId;

    public OrderResponse(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseId() {
        return responseId;
    }

    @JsonCreator
    public static OrderResponse build(@JsonProperty("child_order_acceptance_id")String responseId){
        return new OrderResponse(responseId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
