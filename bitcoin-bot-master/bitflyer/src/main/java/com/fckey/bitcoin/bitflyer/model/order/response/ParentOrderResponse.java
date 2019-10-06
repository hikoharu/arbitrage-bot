package com.fckey.bitcoin.bitflyer.model.order.response;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/15.
 */
public class ParentOrderResponse extends OrderResponse {
    public ParentOrderResponse(String responseId) {
        super(responseId);
    }
    @JsonCreator
    public static ParentOrderResponse build(@JsonProperty("parent_order_acceptance_id")String responseId){
        return new ParentOrderResponse(responseId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
