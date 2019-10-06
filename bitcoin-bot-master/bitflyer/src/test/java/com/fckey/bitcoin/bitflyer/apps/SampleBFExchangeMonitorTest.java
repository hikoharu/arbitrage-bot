package com.fckey.bitcoin.bitflyer.apps;

import com.fckey.bitcoin.bitflyer.api.PublicApiClient;
import com.fckey.bitcoin.bitflyer.model.Board;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fckey on 2016/05/04.
 */
public class SampleBFExchangeMonitorTest {

    private SampleBFExchangeMonitor monitor;
    private Board board;

    @Before
    public void setUp() throws IOException {
        board = new Board(200,
                Arrays.asList(
                        Board.Order.build(100, 3),
                        Board.Order.build(200, 2),
                        Board.Order.build(300, 1)
                ),
                Arrays.asList(
                        Board.Order.build(100, 3),
                        Board.Order.build(200, 5),
                        Board.Order.build(300, 1)
                )
        );

        PublicApiClient publicApiClient = mock(PublicApiClient.class);
        when(publicApiClient.getBoard()).thenReturn(board);
        monitor = new SampleBFExchangeMonitor(publicApiClient);
    }

    @Test
    public void testCalcPrice() {
        int actual = monitor.calcPrice(board.getBids());
        int expected = 166;
        assertThat(actual, is(expected));

        expected = 177;
        actual = monitor.calcPrice(board.getAsks());
        assertThat(actual, is(expected));
    }


}
