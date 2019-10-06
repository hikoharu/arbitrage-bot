package cc.bitbank.entity.enums;

import cc.bitbank.deserializer.OrderTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by tanaka on 2017/04/12.
 */

@JsonDeserialize(using = OrderTypeDeserializer.class)
public enum OrderType {
    LIMIT("limit"),
    MARKET("market");

    private final String type;

    OrderType(final String type) {
        this.type = type;
    }

    public String getCode() {
        return this.type;
    }
}