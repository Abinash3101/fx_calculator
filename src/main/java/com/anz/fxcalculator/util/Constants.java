package com.anz.fxcalculator.util;

import java.util.Arrays;
import java.util.List;

public class Constants {

    private Constants() {
    }

    public static final List<String> SUPPORTED_CURRENCIES =
            Arrays.asList("AUD","CAD","CNY","CZK","DKK","EUR","GBP","JPY","NOK","NZD","USD");
    public static final String DIRECT_FEED = "D";
    public static final String INVERTED = "Inv";
    public static final String UNITY = "1:1";

}
