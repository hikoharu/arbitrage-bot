package com.fckey.bitcoin.bitflyer.model.order;

import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.TimeInForce;
import com.fckey.bitcoin.core.common.BuySell;

import static com.fckey.bitcoin.bitflyer.common.OrderType.LIMIT;
import static com.fckey.bitcoin.bitflyer.common.OrderType.MARKET;
import static com.fckey.bitcoin.bitflyer.common.ProductCode.FX_BTC_JPY;
import static com.fckey.bitcoin.bitflyer.common.TimeInForce.GTC;

/**
 * Created by fckey on 2016/05/02.
 */
public class BFOrderFactory {

    private static BFOrderFactory factory;;
    private static final int DEFAULT_EXPIRE_MIN = 1;
    public static BFOrderFactory getInstance() {
        if (factory == null) {
            factory = new BFOrderFactory();
        }
        return factory;
    }

    public ChildOrder childOrderMkt(BuySell buySell, int price) {
        return new ChildOrder(FX_BTC_JPY, MARKET, buySell, price, DEFAULT_EXPIRE_MIN, GTC);
    }

    public ChildOrder childOrderMkt(BuySell buySell, int price, int minToExpire) {
        return new ChildOrder(FX_BTC_JPY, MARKET, buySell, price, minToExpire, GTC);
    }

    public ChildOrder childOrder(BuySell buySell, int price, double size) {
        return childOrder(FX_BTC_JPY, LIMIT, buySell, price, size, DEFAULT_EXPIRE_MIN, GTC);
    }

    public ChildOrder childOrder(BuySell buySell, int price, double size, int minToExpire) {
        return childOrder(FX_BTC_JPY, LIMIT, buySell, price, size, minToExpire, GTC);
    }

    public ChildOrder childOrder(ProductCode productCode, OrderType orderType, BuySell buySell, int price, double size, int minToExpire, TimeInForce timeInForce) {
        return new ChildOrder(productCode, orderType, buySell, price, size, minToExpire, timeInForce);
    }

    public ParentOrder shortSell() {
        return null;
    }

}
