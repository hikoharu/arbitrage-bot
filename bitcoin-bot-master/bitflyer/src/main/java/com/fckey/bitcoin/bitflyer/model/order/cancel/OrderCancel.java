package com.fckey.bitcoin.bitflyer.model.order.cancel;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fckey on 2016/04/15.
 */
public class OrderCancel {

    public OrderCancel(ProductCode productCode) {
        this.productCode = productCode;
    }

    @JsonProperty("product_code")
    private ProductCode productCode;

    public ProductCode getProductCode() {
        return productCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
