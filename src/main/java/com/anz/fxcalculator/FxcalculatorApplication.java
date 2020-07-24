package com.anz.fxcalculator;

import com.anz.fxcalculator.Exception.InvalidInputException;
import com.anz.fxcalculator.service.ConverterService;
import com.anz.fxcalculator.util.FxUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import static com.anz.fxcalculator.util.Constants.SUPPORTED_CURRENCIES;

@SpringBootApplication
public class FxcalculatorApplication implements CommandLineRunner {

    private final ConverterService converterService;

    public FxcalculatorApplication(ConverterService converterService) {
        this.converterService = converterService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FxcalculatorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Currencies now supported: " + SUPPORTED_CURRENCIES);
        String base;
        String term;
        BigDecimal baseAmount;
        Scanner scanner = new Scanner(System.in);
        while(true) {
            //  prompt for the user's input
            System.out.print("Enter conversion input(pattern: <ccy1> <amount1> in <ccy2> e.g. AUD 100.00 in USD): ");
            // get their input as a String
            String input = scanner.nextLine();
            base = StringUtils.trimWhitespace(input).substring(0, 3);
            term = StringUtils.trimWhitespace(input).substring(input.lastIndexOf(" ") + 1);
            try {
                baseAmount = FxUtility.captureAmount(input);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                continue;
            }
            if(FxUtility.isValidCurrencyTerm(base) && FxUtility.isValidCurrencyTerm(term)) {
                BigDecimal output = null;
                try {
                    output = converterService.convert(base, term, baseAmount);
                } catch (IOException | InvalidFormatException e) {
                    System.out.println("Internal Server error. please contact support..");
                    System.exit(0);
                }
                System.out.println(base+" "+baseAmount+" "+"="+" "+term+" "+output);
            } else {
                System.out.println("Unable to find rate for"+" "+ base+"/"+term);
            }
        }
    }

}
