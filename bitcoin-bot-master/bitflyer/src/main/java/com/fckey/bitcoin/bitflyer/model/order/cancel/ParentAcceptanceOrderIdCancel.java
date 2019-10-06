package com.fckey.bitcoin.bitflyer.model.order.cancel;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/15.
 */
public class ParentAcceptanceOrderIdCancel extends OrderCancel {

    @JsonProperty("parent_order_acceptance_id")
    private String parentOrderAcceptanceId;

    public ParentAcceptanceOrderIdCancel(ProductCode productCode, String parentOrderAcceptanceId) {
        super(productCode);
        this.parentOrderAcceptanceId = parentOrderAcceptanceId;
    }

    public String getParentOrderAcceptanceId() {
        return parentOrderAcceptanceId;
    }
}

