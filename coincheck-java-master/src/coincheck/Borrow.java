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
public class Borrow {

    private CoinCheck client;

    public Borrow(CoinCheck client) {
        this.client = client;
    }

    /**
     * Create a new Borrow.
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject create(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/lending/borrows", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Get a borrowing list.
     *
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject matches() throws Exception {
        String response = this.client.request("GET", "api/lending/borrows/matches", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }

    /**
     * Based on this id, you can repay.
     *
     * @param params
     * @throws java.lang.Exception
     *
     * @return JSONObject
     */
    public JSONObject repay(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/lending/borrows/" + params.getString("id") + "/repay", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}
