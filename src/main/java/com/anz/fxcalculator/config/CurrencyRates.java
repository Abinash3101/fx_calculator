package com.anz.fxcalculator.config;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Map;

@Configuration
@ConfigurationProperties("currency.rates.entries")
@Getter
@Setter
@Generated
public class CurrencyRates {
    private Map<String, BigDecimal> map;
}
