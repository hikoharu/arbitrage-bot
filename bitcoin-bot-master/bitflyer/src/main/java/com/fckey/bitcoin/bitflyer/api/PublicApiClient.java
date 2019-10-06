package com.fckey.bitcoin.bitflyer.api;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.model.Board;
import com.fckey.bitcoin.bitflyer.model.Execution;
import com.fckey.bitcoin.bitflyer.model.Health;
import com.fckey.bitcoin.bitflyer.model.Ticker;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by fckey on 2016/04/02.
 */
public class PublicApiClient extends BitflyerApiClient {

    private static PublicApiClient apiClient;

    public static PublicApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new PublicApiClient();
        }
        return apiClient;
    }

    /**
     *  Extract executions for default product code with default pages
     * @return
     * @throws IOException
     */
    public List<Exception> getExecutions() throws IOException {
        return getExecutions(ProductCode.BTC_JPY);
    }

    /**
     * Extract executions for product code with default pages
     * @param productCode
     * @return
     * @throws IOException
     */
    public List<Exception> getExecutions(ProductCode productCode) throws IOException {
        String jsonString = apiCall("getexecutions" + getProductCodeParam(productCode));
        List<Exception> li = mapper.readValue(jsonString, new TypeReference<List<Execution>>() {
        });
        return li;
    }

    /**
     * Extract executions for product code and/or page request
     * @param prams value could be ProductCode or PageRequest
     * @return
     * @throws IOException
     */
    public List<Exception> getExecutions(Map prams) throws IOException {
        String jsonString = apiCall("getexecutions" + genUrlParam(prams));
        List<Exception> li = mapper.readValue(jsonString, new TypeReference<List<Execution>>() {
        });
        return li;
    }

    /**
     * Extract Health of the exchange
     * @return
     * @throws IOException
     */
    public Health getHealth() throws IOException {
        String jsonString = apiCall("gethealth");
        Health health = mapper.readValue(jsonString, Health.class);
        return health;
    }

    /**
     * Extract board for default product code
     * @return
     * @throws IOException
     */
    public Board getBoard() throws IOException {
        return getBoard(ProductCode.BTC_JPY);
    }

    public Board getEthBtcBoard() throws IOException {
        return getBoard(ProductCode.ETH_BTC);
    }

    
    /**
     *  Extract board for product code
     * @param productCode
     * @return
     * @throws IOException
     */
    public Board getBoard(ProductCode productCode) throws IOException {
        String jsonString = apiCall("getboard" + getProductCodeParam(productCode));
        Board board = mapper.readValue(jsonString, Board.class);
        return board;
    }

    /**
     *  Extract ticker for default product code
     * @return
     * @throws IOException
     */
    public Ticker getTicker() throws IOException {
        return getTicker(ProductCode.BTC_JPY);
    }

    /**
     *  Extract ticker for product code
     * @param productCode
     * @return
     * @throws IOException
     */
    public Ticker getTicker(ProductCode productCode) throws IOException {
        String jsonString = apiCall("getticker" + getProductCodeParam(productCode));
        Ticker ticker = mapper.readValue(jsonString, Ticker.class);
        return ticker;
    }
}