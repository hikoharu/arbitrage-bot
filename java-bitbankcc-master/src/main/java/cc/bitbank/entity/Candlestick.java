package cc.bitbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tanaka on 2017/04/12.
 */
public class Candlestick extends Data {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ohlcvs {
        public BigDecimal[][] ohlcv;
        public String type;

        public class Ohlcv {
            @JsonIgnore
            public int open;
            @JsonIgnore
            public int high;
            @JsonIgnore
            public int low;
            @JsonIgnore
            public int close;
            @JsonIgnore
            public BigDecimal volume;
            @JsonIgnore
            public Date date;

            public Ohlcv(int o, int h, int l, int c, String v, long d) {
                this.open = o;
                this.high = h;
                this.low = l;
                this.close = c;
                this.volume = new BigDecimal(v);
                this.date = new Date(d);
            }

            public String toString() {
                return "[Ohlcv] open " + open + ", high " + high + ", low " + low + ", close " + close +
                        ", volume " + volume + ", date " + date;
             }
        }

        public List<Ohlcv> getOhlcvList() {
            List<Ohlcv> list = new ArrayList();
            for(BigDecimal[] i : this.ohlcv) {
                list.add(
                    new Ohlcv(i[0].intValue(), i[1].intValue(), i[2].intValue(), i[3].intValue(),
                        i[4].toString(), i[5].longValue())
                );
            }
            return list;
        }

    }

    public Ohlcvs[] candlestick;
    public Date timestamp;

    Candlestick() {}
    Candlestick(Ohlcvs[] o) {
        this.candlestick = o;
    }
}
