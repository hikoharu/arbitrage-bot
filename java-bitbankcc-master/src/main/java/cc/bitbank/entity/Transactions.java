package cc.bitbank.entity;

import cc.bitbank.entity.enums.OrderSide;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tanaka on 2017/04/12.
 */
public class Transactions extends Data {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Transaction {
        @JsonProperty("transaction_id")
        public long transactionId;

        public OrderSide side;

        public int price;

        public BigDecimal amount;

        @JsonProperty("executed_at")
        public Date executedAt;

        public String toString() {
            return "[Transaction] transaction_id " + transactionId + ", side " + side.getCode() + ", price " + price + ", amount " +
                    amount + ", executed_at " + executedAt.toString();
        }
    }

    public Transaction[] transactions;

    public Transactions () {}
    public Transactions (Transaction[] transactions) {
        this.transactions = transactions;
    }
}
