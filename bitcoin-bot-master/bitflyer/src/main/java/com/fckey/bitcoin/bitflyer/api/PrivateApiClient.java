package com.fckey.bitcoin.bitflyer.api;

import static com.fckey.bitcoin.core.common.HttpMethod.GET;
import static com.fckey.bitcoin.core.common.HttpMethod.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;

import com.fckey.bitcoin.bitflyer.api.BitflyerRequestHeaderFactory.BitflyerRequestHeaderElement;
import com.fckey.bitcoin.bitflyer.common.ProductCode;
import com.fckey.bitcoin.bitflyer.common.State;
import com.fckey.bitcoin.bitflyer.model.Balance;
import com.fckey.bitcoin.bitflyer.model.Collateral;
import com.fckey.bitcoin.bitflyer.model.OwnExecution;
import com.fckey.bitcoin.bitflyer.model.Position;
import com.fckey.bitcoin.bitflyer.model.order.ChildOrder;
import com.fckey.bitcoin.bitflyer.model.order.ParentOrder;
import com.fckey.bitcoin.bitflyer.model.order.cancel.ChildAcceptanceOrderIdCancel;
import com.fckey.bitcoin.bitflyer.model.order.cancel.ChildOrderIdCancel;
import com.fckey.bitcoin.bitflyer.model.order.cancel.OrderCancel;
import com.fckey.bitcoin.bitflyer.model.order.cancel.ParentAcceptanceOrderIdCancel;
import com.fckey.bitcoin.bitflyer.model.order.cancel.ParentOrderIdCancel;
import com.fckey.bitcoin.bitflyer.model.order.response.OrderResponse;
import com.fckey.bitcoin.bitflyer.model.order.response.ParentOrderResponse;
import com.fckey.bitcoin.bitflyer.model.order.state.ChildOrderState;
import com.fckey.bitcoin.bitflyer.model.order.state.ParentOrderState;
import com.fckey.bitcoin.bitflyer.model.order.state.ParentOrdersState;
import com.fckey.bitcoin.core.common.HttpMethod;

/**
 * Created by fckey on 2016/04/07.
 */
public class PrivateApiClient extends BitflyerApiClient {

    BitflyerRequestHeaderFactory headerFactory;

    private static PrivateApiClient apiClient;


    public static PrivateApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new PrivateApiClient();
        }
        return apiClient;
    }

    public PrivateApiClient() {
        headerFactory = BitflyerRequestHeaderFactory.getInstance();
    }

    public List<Balance> getBalance() throws IOException {
        String apiName = "getbalance";
        String url = PRIVATE_BASE_URL + apiName;
        String jsonString = requestGetByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), GET.name(), "")).build());
        List<Balance> balances = mapper.readValue(jsonString, new TypeReference<List<Balance>>() {
        });
        return balances;
    }

    public Collateral getCollatetal() throws IOException {
        String apiName = "getcollateral";
        String url = PRIVATE_BASE_URL + apiName;
        String jsonString = requestGetByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), GET.name(), "")).build());
        Collateral collateral = mapper.readValue(jsonString, Collateral.class);
        return collateral;
    }

    /**
     * POST child order
     *
     * @param order
     */
    public OrderResponse sendChildOrder(ChildOrder order) throws IOException {
        String apiName = "sendchildorder";
        String url = PRIVATE_BASE_URL + apiName;
        String body = mapper.writeValueAsString(order);
        System.out.println(body);

        String jsonString = requestPostByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), POST.name(), body)).build(), body);
        OrderResponse response = mapper.readValue(jsonString, OrderResponse.class);
        return response;
    }

    public ParentOrderResponse sendParentOrder(ParentOrder order) throws IOException {
        String apiName = "sendparentorder";
        String url = PRIVATE_BASE_URL + apiName;
        String body = mapper.writeValueAsString(order);
        String jsonString = requestPostByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), POST.name(), body)).build(), body);
        ParentOrderResponse response = mapper.readValue(jsonString, ParentOrderResponse.class);
        return response;
    }

    public void cancelAll(OrderCancel cancel) throws IOException {
        String apiName = "cancelallchildorders";
        String url = PRIVATE_BASE_URL + apiName;
        String body = mapper.writeValueAsString(cancel);
        requestPostByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), POST.name(), body)).build(), body);
    }

    public void cancelByChildOrderId(ChildOrderIdCancel cancel) throws IOException {
        String apiName = "cancelchildorder";
        String url = PRIVATE_BASE_URL + apiName;
        String body = mapper.writeValueAsString(cancel);
        requestPostByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), POST.name(), body)).build(), body);
    }

    public void cancelByChildAcceptanceId(ChildAcceptanceOrderIdCancel cancel) throws IOException {
        String apiName = "cancelchildorder";
        String url = PRIVATE_BASE_URL + apiName;
        String body = mapper.writeValueAsString(cancel);
        requestPostByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), POST.name(), body)).build(), body);
    }

    public void cancelByParentOrderId(ParentOrderIdCancel cancel) throws IOException {
        String apiName = "cancelparentorder";
        String url = PRIVATE_BASE_URL + apiName;
        String body = mapper.writeValueAsString(cancel);
        requestPostByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), POST.name(), body)).build(), body);
    }

    public void cancelByParentAcceptanceId(ParentAcceptanceOrderIdCancel cancel) throws IOException {
        String apiName = "cancelparentorder";
        String url = PRIVATE_BASE_URL + apiName;
        String body = mapper.writeValueAsString(cancel);
        requestPostByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), POST.name(), body)).build(), body);
    }

    public List<ChildOrderState> getChildOrderStates() throws IOException {
        String apiName = "getchildorders";
        String url = PRIVATE_BASE_URL + apiName;
        String jsonString = requestGetByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), GET.name(), "")).build());
        List<ChildOrderState> orderStates = mapper.readValue(jsonString, new TypeReference<List<ChildOrderState>>() {
        });
        return orderStates;
    }

    public List<ChildOrderState> getChildOrderStatesForProduct(ProductCode productCode) throws IOException {
        String apiName = "getchildorders";
        String privateApiPath = getPrivateApiPath(apiName) + getProductCodeParam(productCode);
        String jsonString = queryPrivateApiForPath(privateApiPath, GET);
        List<ChildOrderState> orderStates = mapper.readValue(jsonString, new TypeReference<List<ChildOrderState>>() {
        });
        return orderStates;
    }

    public List<ChildOrderState> getChildOrderStates(ProductCode productCode, State state) throws IOException {
        String apiName = "getchildorders";
        List<ChildOrderState> orderStates = new ArrayList<>();
        HashMap<String, String> params = new HashMap<String, String>() {{
            put(PRODUCT_CODE_PARAM, productCode.name());
            put(CHILD_ORDER_STATE_PARAM, state.name());
        }};
        String jsonString = queryPrivateApiWithParams(apiName, GET, params);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString, new TypeReference<List<ChildOrderState>>() {
            }));
        }
        return orderStates;
    }

    public List<ChildOrderState> getChildOrderStates(ProductCode productCode, String childOrderAcceptanceId) throws IOException {
        String apiName = "getchildorders";
        List<ChildOrderState> orderStates = new ArrayList<>();
        HashMap<String, String> params = new HashMap<String, String>() {{
            put(PRODUCT_CODE_PARAM, productCode.name());
            put(CHILD_ORDER_ACCEPTANCE_ID_PARAM, childOrderAcceptanceId);
        }};
        String jsonString = queryPrivateApiWithParams(apiName, GET, params);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString, new TypeReference<List<ChildOrderState>>() {
            }));
        }
        return orderStates;
    }
    
    public List<ParentOrderState> getParentOrderStates() throws IOException {
        String apiName = "getparentorders";
        String url = PRIVATE_BASE_URL + apiName;
        String jsonString = requestGetByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(getPrivateApiPath(apiName), GET.name(), "")).build());
        List<ParentOrderState> orderStates = mapper.readValue(jsonString, new TypeReference<List<ParentOrderState>>() {
        });
        return orderStates;
    }

    public List<ParentOrderState> getParentOrderStatesForProduct(ProductCode productCode) throws IOException {
        String apiName = "getparentorders";
        String privateApiPath = getPrivateApiPath(apiName) + getProductCodeParam(productCode);
        String jsonString = queryPrivateApiForPath(privateApiPath, GET);
        List<ParentOrderState> orderStates = mapper.readValue(jsonString, new TypeReference<List<ParentOrderState>>() {
        });
        return orderStates;
    }



    public List<ParentOrderState> getParentOrderStatesForProductAndState(ProductCode productCode, State state) throws IOException {
        String apiName = "getparentorders";
        List<ParentOrderState> orderStates = new ArrayList<>();
        HashMap<String, String> params = new HashMap<String, String>() {{
            put(PRODUCT_CODE_PARAM, productCode.name());
            put(PARENT_ORDER_STATE_PARAM, state.name());
        }};
        String jsonString = queryPrivateApiWithParams(apiName, GET, params);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString,
                    new TypeReference<List<ParentOrderState>>() {}));
        }
        return orderStates;
    }

    public ParentOrdersState getParentOrdersStatesByOrderId(String orderId) throws IOException {
        String apiName = "getparentorder";
        HashMap<String, String> params = new HashMap<String, String>() {{
            put(PARENT_ORDER_ID_PATH, orderId);
        }};
        String jsonString = queryPrivateApiWithParams(apiName, GET, params);
        ParentOrdersState orderState = mapper.readValue(jsonString, ParentOrdersState.class);
        return orderState;
    }

    public ParentOrdersState getParentOrdersStatesByAcceptanceId(String acceptanceId) throws IOException {
        String apiName = "getparentorder";
        HashMap<String, String> params = new HashMap<String, String>() {{
            put(CHILD_ORDER_ACCEPTANCE_ID_PARAM, acceptanceId);
        }};
        String jsonString = queryPrivateApiWithParams(apiName, GET, params);
        ParentOrdersState orderState = mapper.readValue(jsonString, ParentOrdersState.class);
        return orderState;
    }

    public List<OwnExecution> getOwnExecutions() throws IOException {
        String apiName = "getexecutions";
        List<OwnExecution> orderStates = new ArrayList<>();
        String jsonString = queryPrivateApiForPath(apiName, GET);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString,
                    new TypeReference<List<OwnExecution>>() {}));
        }
        return orderStates;
    }

    public List<OwnExecution> getOwnExecutions(ProductCode productCode) throws IOException {
        String apiName = "getexecutions";
        List<OwnExecution> orderStates = new ArrayList<>();
        String privateApiPath = getPrivateApiPath(apiName) + getProductCodeParam(productCode);

        String jsonString = queryPrivateApiForPath(privateApiPath, GET);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString,
                    new TypeReference<List<OwnExecution>>() {}));
        }
        return orderStates;
    }

    public List<OwnExecution> getOwnExecutionsByOrderId(String orderId) throws IOException {
        String apiName = "getexecutions";
        List<OwnExecution> orderStates = new ArrayList<>();
        HashMap<String, String> params = new HashMap<String, String>() {{
            put(CHILD_ORDER_STATE_PARAM, orderId);
        }};
        String jsonString = queryPrivateApiWithParams(apiName, GET, params);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString,
                    new TypeReference<List<OwnExecution>>() {}));
        }
        return orderStates;
    }

    public List<OwnExecution> getOwnExecutionsByAcceptanceId(ProductCode productCode, String acceptanceId) throws IOException {
        String apiName = "getexecutions";
        List<OwnExecution> orderStates = new ArrayList<>();
        HashMap<String, String> params = new HashMap<String, String>() {{
            put(PRODUCT_CODE_PARAM, productCode.name());
            put(CHILD_ORDER_ACCEPTANCE_ID_PARAM, acceptanceId);
        }};
        String jsonString = queryPrivateApiWithParams(apiName, GET, params);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString,
                    new TypeReference<List<OwnExecution>>() {}));
        }
        return orderStates;
    }

    /**
     * Default: FX_BTC_JPYc
     * @return
     * @throws IOException
     */
    public List<Position> getPositions() throws IOException {
        String apiName = "getpositions";
        List<Position> orderStates = new ArrayList<>();
        String privateApiPath = getPrivateApiPath(apiName) + getProductCodeParam(ProductCode.FX_BTC_JPY);

        String jsonString = queryPrivateApiForPath(privateApiPath, GET);
        if(!StringUtils.isEmpty(jsonString)) {
            orderStates.addAll(mapper.readValue(jsonString,
                    new TypeReference<List<Position>>() {}));
        }
        return orderStates;
    }

    String queryPrivateApiWithParams(String apiName, HttpMethod method, HashMap<String, String> params) {
        String privateApiPath = getPrivateApiPath(apiName) + genUrlParam(params);
        return queryPrivateApiForPath(privateApiPath, method);
    }

    String queryPrivateApiForPath(String privateApiPath, HttpMethod method) {
        String url = BITFLYER_URL + privateApiPath ;
        return requestGetByUrlWithHeader(url, headerFactory.setElement(new BitflyerRequestHeaderElement(privateApiPath, method.name(), "")).build());
    }

    protected String getPrivateApiPath(String apiName) {
        return PRIVATE_PATH_PREFIX + apiName;
    }

}
