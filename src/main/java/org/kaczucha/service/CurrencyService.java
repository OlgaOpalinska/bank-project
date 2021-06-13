package org.kaczucha.service;

import lombok.RequiredArgsConstructor;
import org.kaczucha.controller.dto.CurrencyResponse;
import org.kaczucha.repository.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final RestTemplate restTemplate;

    public CurrencyResponse getCurrencyRates() {
        final ResponseEntity<CurrencyResponse> response = restTemplate.getForEntity(
                "http://api.exchangeratesapi.io/latest?access_key=84f93a8b364401ae71a19d5101656a34&format=1",
                CurrencyResponse.class);
        return response.getBody();
    }

    public double calculateExchangeRate(String fromCurrency, String toCurrency) {
        final Map<String, Double> rates = getCurrencyRates().getRates();
        final Double fromRate = rates.get(fromCurrency);
        final Double toRate = rates.get(toCurrency);
        return toRate / fromRate;
    }
}
