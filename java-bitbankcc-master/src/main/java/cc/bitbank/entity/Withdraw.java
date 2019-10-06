package cc.bitbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tanaka on 2017/04/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Withdraw extends Data {

    public String uuid;
    public String asset;
    @JsonProperty("account_uuid")
    public String accountUuid;
    public BigDecimal amount;
    public BigDecimal fee;
    public String label;
    public String address;
    public String txid;
    public String status;
    @JsonProperty("requested_at")
    public Date requestedAt;

    public String toString() {
        return "[Withdraw] uuid " + uuid + ", asset " + asset + ", account_uuid " + accountUuid +
                ", amount " + amount + ", fee " + fee + ", label " + label + ", address " + address + ", status" + status;
    }
}
