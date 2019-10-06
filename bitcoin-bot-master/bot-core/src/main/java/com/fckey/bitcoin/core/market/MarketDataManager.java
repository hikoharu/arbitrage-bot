package com.fckey.bitcoin.core.market;

import com.fckey.bitcoin.core.model.MarketPrice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fckey on 2016/05/01.
 */
public class MarketDataManager {

    public static final int MAX_SIZE = 1_000;
    private static MarketDataManager marketDataManager;
    private List<MarketPrice> market = new LinkedList<>();
    private MarketPrice latest;
    private MarketPrice secondlatest;


    public static MarketDataManager getInstance() {
        if (marketDataManager == null) {
            marketDataManager = new MarketDataManager();
        }
        return marketDataManager;
    }

    public void update(int price) {
        update(new MarketPrice(price));
    }

    public void update(MarketPrice price) {
        market.add(price);
        secondlatest = latest;
        latest = price;
        if (market.size() > MAX_SIZE) {
            market.remove(0);
        }
    }

    public MarketPrice get(int i) {
        if (i > market.size()) {
            return null;
        }
        return market.get(i);
    }

    /**
     * Get prev n from the latest
     * @param i
     * @return
     */
    public MarketPrice getPrev(int i) {
        int index = (market.size() - 1) - i;
        if (index < 0) {
            return null;
        }
        return market.get(index);
    }

    public List<MarketPrice> lastNPrices(int i) {
        List<MarketPrice> li = new ArrayList<>();
        int size = size();
        for (int j = size - i; j < size; j++) {
            li.add(get(j));
        }
        return li;
    }


    public MarketPrice getLatest() {
        return latest;
    }

    public MarketPrice getSecondlatest() {
        return secondlatest;
    }

    public boolean isEmpty() {
        return market.isEmpty();
    }

    public int size() {
        return market.size();
    }

}
