package com.fckey.bitcoin.bitflyer.api;

import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.core.api.ApiClientBase;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by fckey on 2016/04/05.
 */
public class BitflyerApiClient extends ApiClientBase {

    public static final String BITFLYER_URL = "https://api.bitflyer.jp";
    public static final String PATH_PREFIX = "/v1/";
    public static final String PRIVATE_PATH_PREFIX = PATH_PREFIX + "me/";
    public final static String BASE_URL = BITFLYER_URL + PATH_PREFIX;
    public final static String PRIVATE_BASE_URL = BITFLYER_URL + PRIVATE_PATH_PREFIX;
    public static final String PRODUCT_CODE_PARAM = "product_code";
    public static final String CHILD_ORDER_STATE_PARAM = "child_order_state";
    public static final String PARENT_ORDER_STATE_PARAM = "parent_order_state";
    public static final String CHILD_ORDER_ACCEPTANCE_ID_PARAM = "child_order_acceptance_id";
    public static final String PARENT_ORDER_ID_PATH = "parent_order_id";


    protected ObjectMapper mapper = new ObjectMapper();

    public String apiCall(String request) {
        String url = BASE_URL + request;
        return requestGetByUrlSimple(url);
    }

    protected String getProductCodeParam(ProductCode p) {
        return genUrlParam(PRODUCT_CODE_PARAM + "=" +p.name());
    }

}
