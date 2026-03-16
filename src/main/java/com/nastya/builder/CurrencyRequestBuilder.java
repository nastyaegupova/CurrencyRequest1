package com.nastya.builder;

import com.nastya.model.CurrencyRequest;

import java.util.List;

public class CurrencyRequestBuilder {
    private List<String> baseCurrencies;
    private String target = "MDL";

    public CurrencyRequestBuilder setBaseCurrencies(List<String> baseCurrencies) {
        this.baseCurrencies = baseCurrencies;
        return this;
    }

    public CurrencyRequestBuilder setTarget(String target) {
        this.target = target;
        return this;
    }

    public CurrencyRequest build() {
        if (baseCurrencies == null || baseCurrencies.isEmpty()) {
            throw new IllegalStateException("Base currencies must be set.");
        }
        return new CurrencyRequest(baseCurrencies, target);
    }
}
