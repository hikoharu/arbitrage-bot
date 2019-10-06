package cc.bitbank;



import cc.bitbank.deserializer.JsonDecorder;
import cc.bitbank.entity.*;
import cc.bitbank.entity.enums.CandleType;
import cc.bitbank.entity.enums.CurrencyPair;
import cc.bitbank.entity.enums.OrderSide;
import cc.bitbank.entity.enums.OrderType;
import cc.bitbank.entity.request.CancelBody;
import cc.bitbank.entity.request.CancelsBody;
import cc.bitbank.entity.request.OrderBody;
import cc.bitbank.entity.request.WithdrawBody;
import cc.bitbank.entity.response.*;
import cc.bitbank.exception.BitbankException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by tanaka on 2017/04/10.
 */
public class Bitbankcc {
    private String apiKey = "";
    private String apiSecret = "";
    private String endPointPublic;
    private String endPointPrivate;

    public Bitbankcc() {
        this.endPointPublic = "public.bitbank.cc";
        this.endPointPrivate = "api.bitbank.cc";
    }

    public Bitbankcc setKey(String key, String secret) {
        this.apiKey = key;
        this.apiSecret = secret;
        return this;
    }

    private URIBuilder getPublicUriBuilder(String path) {
        URIBuilder builder = new URIBuilder();
        return builder.setScheme("https")
                .setHost(this.endPointPublic)
                .setPath(path);
    }

    private URIBuilder getPrivateUriBuilder(String path) {
        URIBuilder builder = new URIBuilder();
        return builder.setScheme("https")
                .setHost(this.endPointPrivate)
                .setPath(path);
    }

    private List<Header> getPublicRequestHeader() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("content-type","application/json; charset=utf-8"));
        return headers;
    }

    private List<Header> makePrivateRequestHeader(long nonce, String sign) throws BitbankException {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("content-type","application/json; charset=utf-8"));
        headers.add(new BasicHeader("ACCESS-KEY", this.apiKey));
        headers.add(new BasicHeader("ACCESS-NONCE", String.valueOf(nonce)));
        headers.add(new BasicHeader("ACCESS-SIGNATURE", sign));
        return headers;
    }

    private List<Header> getPrivateRequestHeader(String path, List<NameValuePair> query) throws BitbankException {
        long nonce = System.currentTimeMillis();
        String queryString = URLEncodedUtils.format(query, "utf-8");
        if(query.size() > 0) queryString = "?" + queryString;
        String message = String.valueOf(nonce) + path + queryString;
        return makePrivateRequestHeader(nonce, createSign(message));
    }

    private List<Header> getPrivateRequestHeader(String json) throws BitbankException {
        long nonce = System.currentTimeMillis();
        String message = String.valueOf(nonce) + json;
        return makePrivateRequestHeader(nonce, createSign(message));
    }

    private String createSign(String message) throws BitbankException {
        try {
            String algo = "HmacSHA256";
            String secret = this.apiSecret;

            SecretKeySpec sk = new SecretKeySpec(secret.getBytes(), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(sk);
            byte[] macBytes = mac.doFinal(message.getBytes());

            StringBuilder sb = new StringBuilder(2 * macBytes.length);
            for(byte b: macBytes) {
                sb.append(String.format("%02x", b&0xff) );
            }
            return sb.toString();
        } catch (Exception e) {
            throw new BitbankException(e.getMessage());
        }
    }

    private <T extends Response> T httpExecute(HttpClient client, HttpRequestBase http, Class<T> clazz) throws BitbankException, IOException {
        try {
            HttpResponse response = client.execute(http);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            //System.out.println(json);

            JsonDecorder decorder = new JsonDecorder();
            T result = decorder.decode(json, clazz);
            if (result == null) {
                ErrorCodeResponse error = decorder.decode(json, ErrorCodeResponse.class);
                throw new BitbankException(error.data.code);
            } else {
                return result;
            }
        } catch (IOException e) {
            //System.out.println(e.getLocalizedMessage());
            throw e;
        }
    }

    private <T extends Response> T doHttpGet(URIBuilder builder, Class<T> clazz, List<Header> header) throws BitbankException, IOException {
        try {
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            HttpClient client = HttpClientBuilder.create().setDefaultHeaders(header).build();
            return httpExecute(client, httpGet, clazz);
        } catch (URISyntaxException e) {
            throw new BitbankException(e.getMessage());
        }
    }

    private <T extends Response> T doHttpPost(URIBuilder builder, Class<T> clazz, List<Header> header, StringEntity entityBody) throws BitbankException, IOException {
        try {
            URI uri = builder.build();
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(entityBody);
            HttpClient client = HttpClientBuilder.create().setDefaultHeaders(header).build();
            return httpExecute(client, httpPost, clazz);
        } catch (URISyntaxException e) {
            throw new BitbankException(e.getMessage());
        }
    }

    public Ticker getTicker(CurrencyPair pair) throws BitbankException, IOException {
        String path = "/" + pair.getCode() + "/ticker";
        URIBuilder builder = getPublicUriBuilder(path);
        TickerResponse result = doHttpGet(builder, TickerResponse.class, getPublicRequestHeader());
        return result.data;
    }

    public Depth getDepth(CurrencyPair pair) throws BitbankException, IOException {
        String path = "/" + pair.getCode() + "/depth";
        URIBuilder builder = getPublicUriBuilder(path);
        DepthResponse result = doHttpGet(builder, DepthResponse.class, getPublicRequestHeader());
        return result.data;
    }

    public Transactions getTransaction(CurrencyPair pair) throws BitbankException, IOException {
        String path = "/" + pair.getCode() + "/transactions";
        URIBuilder builder = getPublicUriBuilder(path);
        TransactionsResponse result = doHttpGet(builder, TransactionsResponse.class, getPublicRequestHeader());
        return result.data;
    }

    public Transactions getTransaction(CurrencyPair pair, String YYYYMMDD) throws BitbankException, IOException {
        String path = "/" + pair.getCode() + "/transactions/" + YYYYMMDD;
        URIBuilder builder = getPublicUriBuilder(path);
        TransactionsResponse result = doHttpGet(builder, TransactionsResponse.class, getPublicRequestHeader());
        return result.data;
    }

    public Candlestick getCandlestick(CurrencyPair pair, CandleType candleType, String YYYYMMDD) throws BitbankException, IOException {
        String path = "/" + pair.getCode() + "/candlestick/" + candleType.getCode() + "/" + YYYYMMDD;
        URIBuilder builder = getPublicUriBuilder(path);
        CandlestickResponse result = doHttpGet(builder, CandlestickResponse.class, getPublicRequestHeader());
        return result.data;
    }

    public Assets getAsset() throws BitbankException, IOException {
        String path = "/v1/user/assets";
        URIBuilder builder = getPrivateUriBuilder(path);
        AssetsResponse result = doHttpGet(builder, AssetsResponse.class,
                getPrivateRequestHeader(path, Collections.<NameValuePair>emptyList()));
        return result.data;
    }

    public Order getOrder(CurrencyPair pair, long id) throws BitbankException, IOException {
        String path = "/v1/user/spot/order";
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("pair", pair.getCode()));
        nameValuePair.add(new BasicNameValuePair("order_id", String.valueOf(id)));

        URIBuilder builder = getPrivateUriBuilder(path).setParameters(nameValuePair);
        OrderResponse result = doHttpGet(builder, OrderResponse.class, getPrivateRequestHeader(path, nameValuePair));
        return result.data;
    }

    public Orders getOrders(CurrencyPair pair, long[] orderIds) throws BitbankException, IOException {
        String path = "/v1/user/spot/orders_info";
        URIBuilder builder = getPrivateUriBuilder(path);

        String json = new CancelsBody(pair, orderIds).toJson();
        StringEntity entity = new StringEntity(json);
        OrdersResponse result = doHttpPost(builder, OrdersResponse.class, getPrivateRequestHeader(json), entity);
        return result.data;
    }

    public Order sendOrder(CurrencyPair pair, double price, BigDecimal amount, OrderSide side, OrderType type)
            throws BitbankException, IOException {
        String path = "/v1/user/spot/order";
        URIBuilder builder = getPrivateUriBuilder(path);

        String json = new OrderBody(pair, amount, price, side, type).toJson();
        StringEntity entity = new StringEntity(json);
        OrderResponse result = doHttpPost(builder, OrderResponse.class, getPrivateRequestHeader(json), entity);
        return result.data;
    }

    public Order cancelOrder(CurrencyPair pair, long orderId) throws BitbankException, IOException {
        String path = "/v1/user/spot/cancel_order";
        URIBuilder builder = getPrivateUriBuilder(path);

        String json = new CancelBody(pair, orderId).toJson();
        StringEntity entity = new StringEntity(json);
        OrderResponse result = doHttpPost(builder, OrderResponse.class, getPrivateRequestHeader(json), entity);
        return result.data;
    }

    public Orders cancelOrders(CurrencyPair pair, long[] orderIds) throws BitbankException, IOException {
        String path = "/v1/user/spot/cancel_orders";
        URIBuilder builder = getPrivateUriBuilder(path);

        String json = new CancelsBody(pair, orderIds).toJson();
        StringEntity entity = new StringEntity(json);
        OrdersResponse result = doHttpPost(builder, OrdersResponse.class, getPrivateRequestHeader(json), entity);
        return result.data;
    }

    public Orders getActiveOrders(CurrencyPair pair, Map<String, Long> option) throws BitbankException, IOException {
        String path = "/v1/user/spot/active_orders";
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("pair", pair.getCode()));
        for(Map.Entry<String, Long> e : option.entrySet()) {
            nameValuePair.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }
        URIBuilder builder = getPrivateUriBuilder(path).setParameters(nameValuePair);
        OrdersResponse result = doHttpGet(builder, OrdersResponse.class, getPrivateRequestHeader(path, nameValuePair));
        return result.data;
    }

    public Accounts getWithdrawalAccounts(String asset) throws BitbankException, IOException {
        String path = "/v1/user/withdrawal_account";
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("asset", asset));

        URIBuilder builder = getPrivateUriBuilder(path).addParameters(nameValuePair);
        AccountsResponse result = doHttpGet(builder, AccountsResponse.class,
                getPrivateRequestHeader(path, nameValuePair));
        return result.data;
    }

    public Withdraw requestWithdraw(String asset, String uuid, BigDecimal amount, String otpToken, String smsToken)
            throws BitbankException, IOException {
        String path = "/v1/user/request_withdrawal";
        URIBuilder builder = getPrivateUriBuilder(path);

        String json = new WithdrawBody(asset, uuid, amount, otpToken, smsToken).toJson();
        StringEntity entity = new StringEntity(json);
        WithdrawResponse result = doHttpPost(builder, WithdrawResponse.class, getPrivateRequestHeader(json), entity);
        return result.data;
    }
}
