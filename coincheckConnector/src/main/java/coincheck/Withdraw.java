/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Withdraw {

    private CoinCheck client;

    public Withdraw(CoinCheck client) {
        this.client = client;
    }

    /**
     * Create a new Withdraw.
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject create(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/withdraws", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Get a withdraw list.
     *
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject all() throws Exception {
        String response = this.client.request("GET", "api/withdraws", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Based on this id, you can repay.
     *
     * @param id
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject cancel(String id) throws Exception {
        String response = this.client.request("DELETE", "api/withdraws/" + id, "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

}
