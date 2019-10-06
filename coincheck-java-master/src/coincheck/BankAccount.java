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
public class BankAccount {

    private final CoinCheck client;
    
    public BankAccount(CoinCheck client) {
        this.client = client;
    }
    
     /**
     * Create a new BankAccount.
     *
     * @param params
     * @throws java.lang.Exception
     * 
     * @return JSONObject
     */
    public JSONObject create(JSONObject params) throws Exception {
        String response = this.client.request("POST", "api/bank_accounts", params.toString());
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
    
    /**
     * Get account information.
     *
     * @throws java.lang.Exception
     * 
     * @return JSONObject
     */
    public JSONObject all() throws Exception {
        String response = this.client.request("GET", "api/bank_accounts", "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
    
    /**
     * Delete a BankAccount.
     *
     * @param id
     * @throws java.lang.Exception
     * 
     * @return JSONObject
     */
    public JSONObject delete(String id) throws Exception {
        String response = this.client.request("DELETE", "api/bank_accounts/" + id, "");
        JSONObject jsonObj = new JSONObject(response);
        return jsonObj;
    }
}
