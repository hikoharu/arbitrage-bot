package com.fckey.bitcoin.core.strategy;

import com.fckey.bitcoin.core.common.BuySell;
import com.fckey.bitcoin.core.strategy.model.OrderSuggestion;
import com.fckey.bitcoin.core.strategy.model.StrategyInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by fckey on 2016/05/05.
 */
public class StrategyAggregator implements InvestmentStrategy {

    private static StrategyAggregator aggregator;
    private List<InvestmentStrategy> strategies;

    public StrategyAggregator() {
        this.strategies = new ArrayList<>();
    }

    public StrategyAggregator(List<InvestmentStrategy> strategies) {
        this.strategies = strategies;
    }

    /**
     * Earlier addition has higher priority than later.
     * @param strategy
     * @return
     */
    public static StrategyAggregator add(InvestmentStrategy strategy) {
        if (aggregator == null) {
            aggregator = new StrategyAggregator();
        }
        aggregator.addStragegy(strategy);
        return aggregator;
    }

    public static StrategyAggregator build() {
        StrategyAggregator agg = StrategyAggregator.aggregator;
        StrategyAggregator.aggregator = null;
        return agg;
    }

    private void addStragegy(InvestmentStrategy strategy) {
        strategies.add(strategy);
    }

    @Override
    public Optional<OrderSuggestion> evaluate(StrategyInput input) {
        OrderSuggestion suggestion = null;
        for (InvestmentStrategy strategy : strategies) {
            Optional<OrderSuggestion> suggOpt = strategy.evaluate(input);
            if (suggOpt.isPresent()) {
                OrderSuggestion candidate = suggOpt.get();
                if (isHolded(candidate)) {//not supposed to take action
                    suggestion = null;
                    break;
                }
                if (suggestion == null || suggestion.getPriority() < candidate.getPriority()) {
                    suggestion = candidate;
                }
            }
        }

        if (suggestion == null) {
            return Optional.empty();
        }
        return Optional.of(suggestion);
    }

    protected boolean isHolded(OrderSuggestion candidate) {
        return candidate.getBuySell().equals(BuySell.UNKNOWN);
    }
}
