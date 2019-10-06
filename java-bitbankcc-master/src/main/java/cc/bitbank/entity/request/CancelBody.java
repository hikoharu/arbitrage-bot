package cc.bitbank.entity.request;

import cc.bitbank.entity.enums.CurrencyPair;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by tanaka on 2017/04/12.
 */
public class CancelBody {
    public String pair;
    @JsonProperty("order_id")
    public long orderId;

    public CancelBody(CurrencyPair pair, long orderId) {
        this.pair = pair.getCode();
        this.orderId = orderId;
    }

    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
