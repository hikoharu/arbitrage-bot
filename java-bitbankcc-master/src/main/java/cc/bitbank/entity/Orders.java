package cc.bitbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by tanaka on 2017/04/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders extends Data {
    public Order[] orders;

    public Orders() {}
    public Orders(Order[] orders) {
        this.orders = orders;
    }
}
