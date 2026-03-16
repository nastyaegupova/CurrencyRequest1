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
        put("USD", new UsdDialog()); //вставили в мап по ключу (Usd ) объект
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

        fetchButton.addActionListener(e -> updateRate()); //когда нажимаем на кнопку покахать курс
    }

    private void updateRate() {
        String selectedCurrency = (String) currencySelector.getSelectedItem();// 1 берем конкретный курс который выбрали в приложении,записали в селектед карренси

        CurrencyRequest req = new CurrencyRequestBuilder()  // 2 создаем запрос
                .setBaseCurrencies(List.of(selectedCurrency))
                .setTarget("MDL")
                .build();

        CurrencyRequestPrototype prototype = new CurrencyRequestPrototype(req); // создается объект prototype и передаем тут наш объект

        CurrencyRequest copy = prototype.copy();


        double rate = CurrencyService.getInstance()
                .getRates(copy)
                .getOrDefault(selectedCurrency, 0.0);// 3  наш запрос передали сюда, в сервисе делается запрос на сайт по нашим требованиям и приходит ответ с нашим курсом

        CurrencyDialog dialog = dialogs.get(selectedCurrency); // возвращаем объект USD dialog
        RateDisplay display = dialog.createDisplay(); // create display с обьектом usd display
        display.setRate(rate); // записываем значение курса

        resultPanel.removeAll();
        resultPanel.add(display.render());
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
