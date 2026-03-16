package com.nastya.service;

import com.google.gson.Gson;
import com.nastya.model.CurrencyRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CurrencyService {
    private static final CurrencyService instance = new CurrencyService();
    private static final String API_KEY = "6ciyaqgTW0eSNLL4IVXUQitR1YXyzEJ8";

    private CurrencyService() {}

    public static CurrencyService getInstance() {
        return instance;
    }

    public Map<String, Double> getRates(CurrencyRequest request) {
        Map<String, Double> results = new HashMap<>();
        for (String base : request.getBaseCurrencies()) {
            try {
                String apiUrl = "https://api.apilayer.com/exchangerates_data/latest?base=" +
                        base + "&symbols=" + request.getTarget();

                System.out.println(" Запрос: " + apiUrl);

                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("apikey", API_KEY);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) { // из reader(наш ответ) записываем наш json
                    json.append(line);
                }

                System.out.println("Ответ JSON:\n" + json);

                ExchangeResponse response = new Gson().fromJson(json.toString(), ExchangeResponse.class);
                if (response != null && response.rates != null) {
                    results.put(base, response.rates.get(request.getTarget()));
                } else {
                    System.err.println("Пустой ответ от API или ошибка парсинга");
                    results.put(base, 0.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                results.put(base, 0.0);
            }
        }
        return results;
    }

    private static class ExchangeResponse {
        Map<String, Double> rates;
    }
}
