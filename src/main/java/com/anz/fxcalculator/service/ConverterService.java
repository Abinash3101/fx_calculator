package com.anz.fxcalculator.service;

import com.anz.fxcalculator.config.CurrencyDecimalFormatValues;
import com.anz.fxcalculator.config.CurrencyRates;
import com.anz.fxcalculator.util.Constants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.anz.fxcalculator.util.Constants.DIRECT_FEED;
import static com.anz.fxcalculator.util.Constants.INVERTED;
import static com.anz.fxcalculator.util.Constants.UNITY;

@Component
public class ConverterService {

    private final CurrencyRates currencyRates;
    private final CurrencyDecimalFormatValues decimalFormatValues;

    public ConverterService(CurrencyRates currencyRates, CurrencyDecimalFormatValues decimalFormatValues) {
        this.currencyRates = currencyRates;
        this.decimalFormatValues = decimalFormatValues;
    }

    public BigDecimal convert(final String base, final String term, final BigDecimal baseAmount) throws IOException, InvalidFormatException {
        String conversionPattern = findConversionPattern(base, term);
        BigDecimal result = new BigDecimal(0);
        if (UNITY.equalsIgnoreCase(conversionPattern)) {
            result = baseAmount;
        } else if (DIRECT_FEED.equalsIgnoreCase(conversionPattern)) {
            result = baseAmount.multiply(currencyRates.getMap().get(base + term)).
                    setScale(decimalFormatValues.getMap().get(term), BigDecimal.ROUND_HALF_EVEN);
        } else if (INVERTED.equalsIgnoreCase(conversionPattern)) {
            result = baseAmount.multiply(BigDecimal.ONE.divide(currencyRates.getMap().get(term + base), 4, RoundingMode.HALF_EVEN))
                    .setScale(decimalFormatValues.getMap().get(term), BigDecimal.ROUND_HALF_EVEN);
        } else if (Constants.SUPPORTED_CURRENCIES.stream().anyMatch(conversionPattern::equalsIgnoreCase)) {
            BigDecimal crossOverValue = convert(base, conversionPattern, baseAmount);
            result = convert(conversionPattern,term,crossOverValue);
        }
        return result;
    }

    private String findConversionPattern(final String base, final String term) throws IOException, InvalidFormatException {
        List<String> baseCurrencies = new ArrayList<>();
        List<String> termsCurrencies = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        String conversionPattern;
        ClassPathResource matrixFile = new ClassPathResource("/matrix.xlsx");
        InputStream inputStream = matrixFile.getInputStream();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                baseCurrencies.add(StringUtils.trimAllWhitespace(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0))));
                termsCurrencies.add(StringUtils.trimAllWhitespace(dataFormatter.formatCellValue(sheet.getRow(0).getCell(i))));
            }
            int baseIndexInMatrix = baseCurrencies.indexOf(base);
            int termIndexInMatrix = termsCurrencies.indexOf(term);
            conversionPattern = StringUtils.trimAllWhitespace(dataFormatter.formatCellValue(sheet.getRow(baseIndexInMatrix).getCell(termIndexInMatrix)));
        } finally {
            inputStream.close();
        }
        return conversionPattern;
    }
}
