package com.nastya.model;

import java.util.List;

public class CurrencyRequest {

    private List<String> baseCurrencies;
    private String target;

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

    public void setBaseCurrencies(List<String> baseCurrencies) {
        this.baseCurrencies = baseCurrencies;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}