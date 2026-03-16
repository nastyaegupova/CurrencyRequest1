package com.nastya.factory;

import javax.swing.*;

public class UsdRateDisplay implements RateDisplay {
    private double rate;

    @Override
    public JPanel render() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("1 USD = " + String.format("%.4f", rate) + " MDL");
        panel.add(label);
        return panel;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }
}
