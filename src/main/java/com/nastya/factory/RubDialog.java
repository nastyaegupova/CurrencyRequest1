package com.nastya.factory;


public class RubDialog extends CurrencyDialog {
    @Override
    public RateDisplay createDisplay() {
        return new RubRateDisplay();
    }
}
