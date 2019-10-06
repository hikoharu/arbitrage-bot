/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Transfer {

    private CoinCheck client;

    public Transfer(CoinCheck client) {
        this.client = client;
    }

    /**
     * Transfer Balance to Leverage.
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject to_leverage(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/exchange/transfers/to_leverage", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Transfer Balance from Leverage.
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject from_leverage(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/exchange/transfers/from_leverage", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

}
