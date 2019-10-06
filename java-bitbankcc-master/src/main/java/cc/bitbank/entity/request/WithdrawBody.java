package cc.bitbank.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

/**
 * Created by tanaka on 2017/04/13.
 */
public class WithdrawBody {
    public String asset;
    public String uuid;
    public BigDecimal amount;
    @JsonProperty("otp_token")
    public String otpToken;
    @JsonProperty("sms_token")
    public String smsToken;

    public WithdrawBody(String asset, String uuid, BigDecimal amount, String otpToken, String smsToken) {
        this.asset = asset;
        this.uuid = uuid;
        this.amount = amount;
        this.otpToken = otpToken;
        this.smsToken = smsToken;
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
