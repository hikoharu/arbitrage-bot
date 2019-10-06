/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coincheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Administrator
 */
public class CoinCheck {

    private final String BASE_API = "https://coincheck.jp/";

    private HttpClient client;

    private String accessKey;

    private String secretKey;

    private Account account;

    private BankAccount bankAccount;

    private Borrow borrow;

    private Deposit deposit;

    private Leverage leverage;

    private Order order;

    private OrderBook orderBook;

    private Send send;

    private Ticker ticker;

    private Trade trade;

    private Transfer transfer;

    private Withdraw withdraw;

    public CoinCheck(String accessKey, String secretKey) throws Exception {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.client = new DefaultHttpClient();
        this.account = new Account(this);
        this.bankAccount = new BankAccount(this);
        this.borrow = new Borrow(this);
        this.deposit = new Deposit(this);
        this.leverage = new Leverage(this);
        this.order = new Order(this);
        this.orderBook = new OrderBook(this);
        this.send = new Send(this);
        this.ticker = new Ticker(this);
        this.trade = new Trade(this);
        this.transfer = new Transfer(this);
        this.withdraw = new Withdraw(this);
        this.deposit = new Deposit(this);
    }

    public String request(String method, String path, String params) throws IOException {
        if (!params.equals("") && method.equals("GET")) {
            path = path + "?" + params;
            params = "";
        }
        String url = BASE_API + path;
        long nonce = System.currentTimeMillis();
        String message = nonce + url + params;
        String signature = Util.createHmacSha256(message, this.secretKey);
        HttpResponse response;
        switch (method) {
            case "GET":
                HttpGet getReq = new HttpGet(url);
                //set get header
                getReq.addHeader("Content-Type", "application/json");
                getReq.addHeader("ACCESS-KEY", this.accessKey);
                getReq.addHeader("ACCESS-NONCE", String.valueOf(nonce));
                getReq.addHeader("ACCESS-SIGNATURE", signature);
                response = this.client.execute(getReq);
                break;
            case "DELETE":
                HttpDelete deleteReq = new HttpDelete(url);
                //set put header
                deleteReq.addHeader("Content-Type", "application/json");
                deleteReq.addHeader("ACCESS-KEY", this.accessKey);
                deleteReq.addHeader("ACCESS-NONCE", String.valueOf(nonce));
                deleteReq.addHeader("ACCESS-SIGNATURE", signature);
                response = this.client.execute(deleteReq);
                break;
            default:
                HttpPost postReq = new HttpPost(url);
                //set post header
                postReq.addHeader("Content-Type", "application/json");
                postReq.addHeader("ACCESS-KEY", this.accessKey);
                postReq.addHeader("ACCESS-NONCE", String.valueOf(nonce));
                postReq.addHeader("ACCESS-SIGNATURE", signature);
                StringEntity entity = new StringEntity(params);
                postReq.setEntity(entity);
                response = this.client.execute(postReq);
        }
       
        //get response body
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        return result.toString();
    }
   
    public Account account() {
        return account;
    }

    public BankAccount bankAccount() {
        return bankAccount;
    }

    public Borrow borrow() {
        return borrow;
    }

    public Leverage leverage() {
        return leverage;
    }

    public Order order() {
        return order;
    }

    public OrderBook orderBook() {
        return orderBook;
    }

    public Send send() {
        return send;
    }

    public Ticker ticker() {
        return ticker;
    }

    public Trade trade() {
        return trade;
    }

    public Transfer transfer() {
        return transfer;
    }

    public Withdraw withdraw() {
        return withdraw;
    }
    
    public Deposit deposit() {
        return deposit;
    }
}
