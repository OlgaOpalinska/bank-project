package org.kaczucha.controller.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class CurrencyResponse {
    private LocalDate date;
    private String base;
    private Map<String, Double> rates;
}
