package cc.bitbank.deserializer;

import cc.bitbank.entity.enums.OrderSide;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by tanaka on 2017/04/12.
 */
public class OrderSideDeserializer extends JsonDeserializer<Object> {
    @Override
    public OrderSide deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        final String jsonValue = jp.getText();
        for (final OrderSide enumValue : OrderSide.values()) {
            if (enumValue.getCode().equals(jsonValue)) {
                return enumValue;
            }
        }
        return null;
    }
}
