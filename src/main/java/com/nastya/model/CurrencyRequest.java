package com.nastya.model;

import java.util.List;

public class CurrencyRequest {
    private final List<String> baseCurrencies;
    private final String target;

    public CurrencyRequest(List<String> baseCurrencies, String target) {
        this.baseCurrencies = baseCurrencies;
        this.target = target;
    }

    public List<String> getBaseCurrencies() {
        return baseCurrencies;
    }

    public String getTarget() {
        return target;
    }
}
