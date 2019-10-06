package com.fckey.bitcoin.bitflyer.model.order.cancel;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/15.
 */
public class ChildAcceptanceOrderIdCancel extends OrderCancel {

    @JsonProperty("child_order_acceptance_id")
    private String childOrderAcceptanceId;

    public ChildAcceptanceOrderIdCancel(ProductCode productCode, String childOrderAcceptanceId) {
        super(productCode);
        this.childOrderAcceptanceId = childOrderAcceptanceId;
    }

    public String getChildOrderAcceptanceId() {
        return childOrderAcceptanceId;
    }
}

