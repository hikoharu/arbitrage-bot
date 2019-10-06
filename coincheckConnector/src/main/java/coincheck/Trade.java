/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Trade {

    private CoinCheck client;

    public Trade(CoinCheck client) {
        this.client = client;
    }

    /**
     * 最新の取引履歴を取得できます。
     *
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public String all() throws Exception {
        String response = this.client.request("GET", "api/trades", "");
        return response;
    }

}
