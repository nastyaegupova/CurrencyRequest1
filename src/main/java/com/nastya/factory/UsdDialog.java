package com.nastya.factory;



public class UsdDialog extends CurrencyDialog {
    @Override
    public RateDisplay createDisplay() {
        return new UsdRateDisplay();
    }
}
