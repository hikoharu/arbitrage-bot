package com.fckey.bitcoin.bitflyer.apps;

import com.fckey.bitcoin.bitflyer.api.PublicApiClient;
import com.fckey.bitcoin.bitflyer.model.Board;
import com.fckey.bitcoin.bitflyer.model.Board.Order;
import com.fckey.bitcoin.bitflyer.model.Ticker;
import com.fckey.bitcoin.core.market.MarketDataManager;
import com.fckey.bitcoin.core.model.MarketPrice;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.fckey.bitcoin.bitflyer.common.ProductCode.FX_BTC_JPY;

/**
 * Created by fckey on 2016/04/20.
 */
public class SampleBFExchangeMonitor {
    public static final int SEC = 1_000;
    public static final int DEFAULT_BOARD_NUM = 3;
    private Timer timer;
    PublicApiClient publicClient;
    private MarketDataManager mktDataMgr;
    private int boardNum;

    public SampleBFExchangeMonitor(PublicApiClient publicClient) {
        this.publicClient = publicClient;
        timer = new Timer("Monitor-Exchange-Thread", false);
        mktDataMgr = MarketDataManager.getInstance();
        boardNum = DEFAULT_BOARD_NUM;

    }

    /**
     * Calculate price as weighted average
     * @param orders
     * @return
     */
    int calcPrice(List<Order> orders) {
        double sizeSum = 0;
        int priceSum = 0;
        int size = Math.min(this.boardNum, orders.size());
        for (int i = 0; i < size; i++) {
            Order order = orders.get(i);
            sizeSum += order.getSize();
            priceSum += order.getPrice() * order.getSize();
        }
        return (int)(priceSum/sizeSum);

    }

    public void run() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Ticker ticker = publicClient.getTicker(FX_BTC_JPY);
                    Board board = publicClient.getBoard(FX_BTC_JPY);

                    MarketPrice mktPrice = new MarketPrice(calcPrice(board.getAsks()), calcPrice(board.getBids()),
                            ticker.getBestAsk(), ticker.getBestAskSize(), ticker.getBestBid(), ticker.getBestBidSize()  );
                    mktDataMgr.update(mktPrice);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SEC, 10 * SEC);
    }
}
