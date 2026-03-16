package com.nastya.factory;

public class EurDialog extends CurrencyDialog {
    @Override
    public RateDisplay createDisplay() {
        return new EurRateDisplay();
    }
}
