package com.anz.fxcalculator.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class FxUtilityTest {

    @Test
    public void shouldValidCurrencyAmount() {
        assertThat(FxUtility.isValidCurrencyAmount("100")).isTrue();
        assertThat(FxUtility.isValidCurrencyAmount("100.00")).isTrue();
        assertThat(FxUtility.isValidCurrencyAmount(null)).isFalse();
        assertThat(FxUtility.isValidCurrencyAmount("23.345")).isTrue();
        assertThat(FxUtility.isValidCurrencyAmount("cbhebe")).isFalse();
        assertThat(FxUtility.isValidCurrencyAmount("123.45")).isTrue();
        assertThat(FxUtility.isValidCurrencyAmount(" ")).isFalse();
    }

    @Test
    public void shouldValidateCurrenyTerm() {
        assertThat(FxUtility.isValidCurrencyTerm("AUD")).isTrue();
        assertThat(FxUtility.isValidCurrencyTerm("CAD")).isTrue();
        assertThat(FxUtility.isValidCurrencyTerm("CNY")).isTrue();
        assertThat(FxUtility.isValidCurrencyTerm("JPY")).isTrue();
        assertThat(FxUtility.isValidCurrencyTerm("GBP")).isTrue();
        assertThat(FxUtility.isValidCurrencyTerm("NZD")).isTrue();
        assertThat(FxUtility.isValidCurrencyTerm(" ")).isFalse();
        assertThat(FxUtility.isValidCurrencyTerm("ckeqjb")).isFalse();
        assertThat(FxUtility.isValidCurrencyTerm("KRW")).isFalse();
        assertThat(FxUtility.isValidCurrencyTerm(null)).isFalse();
    }

    @Test
    public void shouldCaptureAmount() {
        assertThat(FxUtility.captureAmount("AUD 348.00 in USD")).isEqualTo(new BigDecimal("348.00"));
    }
}
