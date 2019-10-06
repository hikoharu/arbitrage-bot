package com.fckey.bitcoin.core.strategy;

import com.fckey.bitcoin.core.strategy.model.OrderSuggestion;
import com.fckey.bitcoin.core.strategy.model.StrategyInput;

import java.util.Optional;

/**
 * Created by fckey on 2016/04/24.
 */
public interface InvestmentStrategy {
    public Optional<OrderSuggestion> evaluate(StrategyInput input);
}
