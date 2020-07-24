package com.anz.fxcalculator.util;

import com.anz.fxcalculator.Exception.InvalidInputException;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

import static com.anz.fxcalculator.util.Constants.SUPPORTED_CURRENCIES;
import static org.apache.commons.lang3.StringUtils.*;

public final class FxUtility {

    public static boolean isValidCurrencyAmount(String amount) {
        return BigDecimalValidator.getInstance().isValid(amount);
    }

    public static boolean isValidCurrencyTerm(String value) {
        return !StringUtils.isEmpty(value) && SUPPORTED_CURRENCIES.stream().anyMatch(value::equalsIgnoreCase);
    }

    public static BigDecimal captureAmount(String input) {
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c) || Character.valueOf(c).equals('.')){
                sb.append(c);
            }
        }
        if(isBlank(sb)) {
            throw new InvalidInputException("Amount is not there in input");
        }
        return new BigDecimal(String.valueOf(sb));
    }
}
