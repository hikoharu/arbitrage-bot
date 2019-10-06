package cc.bitbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tanaka on 2017/04/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker extends Data {
    public int sell;
    public int buy;
    public int high;
    public int low;
    public int last;
    public BigDecimal vol;
    public Date timestamp;

    @Override
    public String toString() {
        return "[Ticker] buy " + buy + ", sell " + sell + ", high " + high + ", low " + low +
                ", last " + last + ", vol " + vol + ", timestamp " + timestamp;
    }
}
