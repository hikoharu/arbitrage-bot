package com.fckey.bitcoin.bitflyer.model.order.cancel;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/15.
 */
public class ChildOrderIdCancel extends OrderCancel {

    public ChildOrderIdCancel(ProductCode productCode, String childOrderId) {
        super(productCode);
        this.childOrderId = childOrderId;
    }

    @JsonProperty("child_order_id")
    private String childOrderId;

    public String getChildOrderId() {
        return childOrderId;
    }
}
