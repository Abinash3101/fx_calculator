package com.anz.fxcalculator;

import com.anz.fxcalculator.service.ConverterService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Scanner;

@SpringBootApplication
public class FxcalculatorApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ConverterService converterService;

    public FxcalculatorApplication(ConverterService converterService) {
        this.converterService = converterService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FxcalculatorApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException, InvalidFormatException {
        //System.out.println("Currencies now supported: " + baseCurrencies);
        String base;
        String term;
        BigDecimal baseAmount;
        Scanner scanner = new Scanner(System.in);

        while(true) {
            //  prompt for the user's name
            System.out.print("Enter conversion input(pattern: <ccy1> <amount1> in <ccy2> e.g. AUD 100.00 in USD): ");

            // get their input as a String
            String input = scanner.nextLine();
            base = StringUtils.trimWhitespace(input).substring(0, 3);
            term = StringUtils.trimWhitespace(input).substring(input.lastIndexOf(" ") + 1);
            baseAmount = captureAmount(input);
            //TODO: validate base, term and baseAmount before passing.
            BigDecimal output = converterService.convert(base, term, baseAmount);
            System.out.println(base+" "+baseAmount+" "+"="+" "+term+" "+output);
        }

    }

    public BigDecimal captureAmount(String input) {
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c) || Character.valueOf(c).equals('.')){
                sb.append(c);
            }
        }
        return new BigDecimal(String.valueOf(sb));
    }
}
