package com.fckey.bitcoin.bitflyer.model;

import com.fckey.bitcoin.bitflyer.common.OrderMethod;
import com.fckey.bitcoin.bitflyer.common.OrderType;
import com.fckey.bitcoin.bitflyer.common.TimeInForce;
import com.fckey.bitcoin.bitflyer.model.order.ChildOrder;
import com.fckey.bitcoin.bitflyer.model.order.ParentOrder;
import com.fckey.bitcoin.bitflyer.model.order.ParentOrder.OrderParameter;
import com.fckey.bitcoin.bitflyer.model.order.cancel.*;
import com.fckey.bitcoin.bitflyer.model.order.response.OrderResponse;
import com.fckey.bitcoin.bitflyer.model.order.response.ParentOrderResponse;
import com.fckey.bitcoin.bitflyer.model.order.state.ChildOrderState;
import com.fckey.bitcoin.bitflyer.model.order.state.ParentOrderState;
import com.fckey.bitcoin.bitflyer.model.order.state.ParentOrdersState;
import com.fckey.bitcoin.core.common.BuySell;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fckey.bitcoin.bitflyer.common.ProductCode.BTC_JPY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by fckey on 2016/04/02.
 */
public class TestModelCreation {

    public static final String ORDER_PATH = "order/";
    public static final String ORDER_RESPONSE_PATH = ORDER_PATH + "response/";
    public static final String ORDER_CANCEL_PATH = ORDER_PATH + "cancel/";
    public static final String STATE_PATH = ORDER_PATH + "state/";

    private ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(
            PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

    @Test
    public void createExecution() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource("Execution.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        mapper.readValue(execStr, Execution.class);
    }

    @Test
    public void createExecutions() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource("Executions.json").toURI());
        String json = FileUtils.readFileToString(inputFile);
        List<Exception> li = mapper.readValue(json, new TypeReference<List<Execution>>() {});
        assertThat(li.size(), is(2));
    }

    @Test
    public void createHealth() throws Exception {
        Health expected = Health.build("NORMAL");
        File inputFile = new File(TestModelCreation.class.getResource("Health.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        mapper.readValue(execStr, Health.class);
    }

    @Test
    public void createBoard() throws Exception {
//        Health expected = Board.build();
        File inputFile = new File(TestModelCreation.class.getResource("Board.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        mapper.readValue(execStr, Board.class);
    }

    @Test
    public void createTicker() throws Exception {
//        Health expected = Board.build();
        File inputFile = new File(TestModelCreation.class.getResource("Ticker.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        mapper.readValue(execStr, Ticker.class);
    }

    @Test
    public void createBalances() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource("Balances.json").toURI());
        String json = FileUtils.readFileToString(inputFile);
        List<Balance> li = mapper.readValue(json, new TypeReference<List<Balance>>() {});
        assertThat(li.size(), is(2));
    }

    @Test
    public void createCollateral() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource("Collateral.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        mapper.readValue(execStr, Collateral.class);
    }

    @Test
    public void createOrderResponses() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource(ORDER_RESPONSE_PATH + "OrderResponse.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        mapper.readValue(execStr, OrderResponse.class);

        inputFile = new File(TestModelCreation.class.getResource(ORDER_RESPONSE_PATH + "ParentOrderResponse.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);
        mapper.readValue(execStr, ParentOrderResponse.class);
    }

    @Test
    public void createOrderStates() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource(STATE_PATH + "ChildOrderStates.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        List<Balance> li = mapper.readValue(execStr, new TypeReference<List<ChildOrderState>>() {});

        inputFile = new File(TestModelCreation.class.getResource(STATE_PATH + "ParentOrderState.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);

        mapper.readValue(execStr, ParentOrderState.class);

        inputFile = new File(TestModelCreation.class.getResource(STATE_PATH + "ParentOrdersStates.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);
        li = mapper.readValue(execStr, new TypeReference<List<ParentOrdersState>>() {});
    }

    @Test
    public void createPositions() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource("Positions.json").toURI());
        String json = FileUtils.readFileToString(inputFile);
        List<Position> li = mapper.readValue(json, new TypeReference<List<Position>>() {});
        assertThat(li.size(), is(1));
    }

    @Test
    public void createOwnExecutions() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource("OwnExecutions.json").toURI());
        String json = FileUtils.readFileToString(inputFile);
        List<Exception> li = mapper.readValue(json, new TypeReference<List<OwnExecution>>() {});
        assertThat(li.size(), is(2));
    }

    /********** POJO to Json *********/
    @Test
    public void createOrders() throws Exception {
        File inputFile = new File(TestModelCreation.class.getResource(ORDER_PATH + "ChildOrder.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        String actual = mapper.writeValueAsString(new ChildOrder(BTC_JPY, OrderType.LIMIT, BuySell.BUY, 30_000, 0.1, 10_000, TimeInForce.GTC));
        verifyStrings("ChildOrder", execStr,actual);

        inputFile = new File(TestModelCreation.class.getResource(ORDER_PATH + "ParentOrder.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);
        actual = mapper.writeValueAsString(new ParentOrder(OrderMethod.IFDOCO,10_000, TimeInForce.GTC,
                new ArrayList<OrderParameter>(Arrays.asList(
                new OrderParameter(BTC_JPY, OrderType.LIMIT, BuySell.BUY, 30_000, 0.1),
                new OrderParameter(BTC_JPY, OrderType.LIMIT, BuySell.SELL, 32_000, 0.1),
                new OrderParameter(BTC_JPY, OrderType.STOP_LIMIT, BuySell.SELL, 28_800, 0.1, 29_000))))
        );
        verifyStrings("ParentOrder", execStr,actual);
    }


    @Test
    public void createOrderCancels() throws Exception{
        File inputFile = new File(TestModelCreation.class.getResource(ORDER_CANCEL_PATH + "ChildAcceptanceOrderIdCancel.json").toURI());
        String execStr = FileUtils.readFileToString(inputFile);
        String actual = mapper.writeValueAsString(new ChildAcceptanceOrderIdCancel(BTC_JPY, "JRF20150707-033333-099999"));
        verifyStrings("ChildAcceptanceOrderIdCancel", execStr,actual);

        inputFile = new File(TestModelCreation.class.getResource(ORDER_CANCEL_PATH + "ChildOrderIdCancel.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);
        actual = mapper.writeValueAsString(new ChildOrderIdCancel(BTC_JPY, "JOR20150707-055555-022222"));
        execStr = StringUtils.remove(execStr, "\n");
        verifyStrings("ChildOrderIdCancel", execStr,actual);

        inputFile = new File(TestModelCreation.class.getResource(ORDER_CANCEL_PATH + "OrderCancel.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);
        actual = mapper.writeValueAsString(new OrderCancel(BTC_JPY));
        execStr = StringUtils.remove(execStr, "\n");
        verifyStrings("OrderCancel", execStr,actual);

        inputFile = new File(TestModelCreation.class.getResource(ORDER_CANCEL_PATH + "ParentAcceptanceOrderIdCancel.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);
        actual = mapper.writeValueAsString(new ParentAcceptanceOrderIdCancel(BTC_JPY, "JRF20150925-033333-099999"));
        execStr = StringUtils.remove(execStr, "\n");
        verifyStrings("ParentAcceptanceOrderIdCancel", execStr,actual);

        inputFile = new File(TestModelCreation.class.getResource(ORDER_CANCEL_PATH + "ParentOrderIdCancel.json").toURI());
        execStr = FileUtils.readFileToString(inputFile);
        actual = mapper.writeValueAsString(new ParentOrderIdCancel(BTC_JPY, "JCO20150925-055555-022222"));
        execStr = StringUtils.remove(execStr, "\n");
        verifyStrings("ParentOrderIdCancel", execStr,actual);
    }


    private void verifyStrings(String scenario, String expected, String actual) {
        assertThat(scenario, actual.replaceAll("\\s+",""), is(expected.replaceAll("\\s+","")));

    }
}
