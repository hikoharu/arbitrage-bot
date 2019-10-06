package com.fckey.bitcoin.bitflyer.model.order.cancel;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/15.
 */
public class ParentOrderIdCancel extends OrderCancel {

    public ParentOrderIdCancel(ProductCode productCode, String parentOrderId) {
        super(productCode);
        this.parentOrderId = parentOrderId;
    }

    @JsonProperty("parent_order_id")
    private String parentOrderId;

    public String getParentOrderId() {
        return parentOrderId;
    }
}
