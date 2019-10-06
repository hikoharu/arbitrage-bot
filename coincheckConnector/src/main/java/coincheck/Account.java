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
public class Account {

    private final CoinCheck client;

    public Account(CoinCheck client) {
        this.client = client;
    }

    /**
     * Make sure a balance.
     *
     * @throws java.lang.Exception
     * @return JSONObject
     */
    public JSONObject balance() throws Exception {
        String response = this.client.request("GET", "api/accounts/balance", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Make sure a leverage balance.
     *
     * @throws java.lang.Exception
     * @return JSONObject
     */
    public JSONObject leverageBalance() throws Exception {
        String response = this.client.request("GET", "api/accounts/leverage_balance", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Get account information.
     *
     * @throws java.lang.Exception
     * @return JSONObject
     */
    public JSONObject info() throws Exception {
        String response = this.client.request("GET", "api/accounts/balance", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}
