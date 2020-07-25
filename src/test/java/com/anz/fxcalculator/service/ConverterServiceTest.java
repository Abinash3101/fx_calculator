package com.anz.fxcalculator.service;

import com.anz.fxcalculator.config.CurrencyDecimalFormatValues;
import com.anz.fxcalculator.config.CurrencyRates;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConverterServiceTest {

    @Test
    public void shouldConvert() throws IOException, InvalidFormatException {
        CurrencyRates currencyRates = new CurrencyRates();
        currencyRates.setMap(createRatesMap());
        CurrencyDecimalFormatValues decimalFormatValues = new CurrencyDecimalFormatValues();
        decimalFormatValues.setMap(createDecimalMap());
        ConverterService service = new ConverterService(currencyRates, decimalFormatValues);
        service.convert("AUD","USD", new BigDecimal("100.00"));
        Assertions.assertThat(service.convert("AUD","USD", new BigDecimal("100.00")))
                .isEqualTo(new BigDecimal("83.71"));
        Assertions.assertThat(service.convert("GBP","NZD", new BigDecimal("32.87")))
                .isEqualTo(new BigDecimal("66.51"));
        Assertions.assertThat(service.convert("AUD","DKK", new BigDecimal("100.00")))
                .isEqualTo(new BigDecimal("505.73"));
        Assertions.assertThat(service.convert("AUD","AUD", new BigDecimal("100.00")))
                .isEqualTo(new BigDecimal("100.00"));
    }

    private Map<String, Integer> createDecimalMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("CAD",2);
        map.put("CNY",2);
        map.put("CZK",2);
        map.put("DKK",2);
        map.put("EUR",2);
        map.put("GBP",2);
        map.put("JPY",0);
        map.put("NOK",2);
        map.put("NZD",2);
        map.put("USD",2);
        map.put("AUD",2);
        return map;
    }

    private Map<String, BigDecimal> createRatesMap() {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("AUDUSD",new BigDecimal("0.8371"));
        map.put("CADUSD",new BigDecimal("0.8711"));
        map.put("USDCNY",new BigDecimal("6.1715"));
        map.put("EURUSD",new BigDecimal("1.2315"));
        map.put("GBPUSD",new BigDecimal("1.5683"));
        map.put("NZDUSD",new BigDecimal("0.7750"));
        map.put("USDJPY",new BigDecimal("119.95"));
        map.put("EURCZK",new BigDecimal("27.6028"));
        map.put("EURDKK",new BigDecimal("7.4405"));
        map.put("EURNOK",new BigDecimal("8.6651"));
        return map;
    }
}
