package com.nastya;

import com.nastya.builder.CurrencyRequestBuilder;
import com.nastya.factory.*;
import com.nastya.model.CurrencyRequest;
import com.nastya.service.CurrencyService;
import com.nastya.prototype.CurrencyRequestPrototype;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CurrencyAppGUI extends JFrame {

    private JComboBox<String> currencySelector;
    private JButton fetchButton;
    private JPanel resultPanel;

    private final Map<String, CurrencyDialog> dialogs = new HashMap<>() {{
        put("USD", new UsdDialog());
        put("EUR", new EurDialog());
        put("RUB", new RubDialog());
    }};

    public CurrencyAppGUI() {

        setTitle("Курс валют → MDL");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        currencySelector = new JComboBox<>(new String[]{"USD", "EUR", "RUB"});
        fetchButton = new JButton("Показать курс");
        resultPanel = new JPanel();

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Выберите валюту:"));
        topPanel.add(currencySelector);
        topPanel.add(fetchButton);

        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        fetchButton.addActionListener(e -> updateRate());
    }

    private void updateRate() {

        String selectedCurrency = (String) currencySelector.getSelectedItem();

        // Builder создаём один раз
        CurrencyRequest req = new CurrencyRequestBuilder()
                .setBaseCurrencies(List.of(selectedCurrency))
                .setTarget("MDL")
                .build();

        // получаем курс к MDL
        Map<String, Double> mdlRates = CurrencyService.getInstance().getRates(req);

        double mdlRate = mdlRates.getOrDefault(selectedCurrency, 0.0);

        double eurRate = 0;

        if (selectedCurrency.equals("USD")) {

            // Prototype — копируем существующий запрос
            CurrencyRequestPrototype prototype = new CurrencyRequestPrototype(req);

            CurrencyRequest copy = prototype.copy();

            // меняем только параметры
            copy.setBaseCurrencies(List.of("EUR"));
            copy.setTarget("USD");

            Map<String, Double> eurRates =
                    CurrencyService.getInstance().getRates(copy);

            eurRate = eurRates.getOrDefault("EUR", 0.0);
        }

        CurrencyDialog dialog = dialogs.get(selectedCurrency);
        RateDisplay display = dialog.createDisplay();

        display.setRate(mdlRate);

        resultPanel.removeAll();
        resultPanel.add(display.render());

        if (selectedCurrency.equals("USD")) {

            JLabel eurLabel =
                    new JLabel("1 EUR = " + eurRate + " USD");

            resultPanel.add(eurLabel);
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            CurrencyAppGUI app = new CurrencyAppGUI();
            app.setVisible(true);
        });
    }
}