package com.anz.fxcalculator.config;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("currency.format.entries")
@Getter
@Setter
@Generated
public class CurrencyDecimalFormatValues {
    private Map<String, Integer> map;
}
