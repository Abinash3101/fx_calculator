package com.anz.fxcalculator.service;

import com.anz.fxcalculator.config.CurrencyRates;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterService {

    private final CurrencyRates currencyRates;

    public ConverterService(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }

    public BigDecimal convert(final String base, final String term, final BigDecimal baseAmount) throws IOException, InvalidFormatException {
        String conversionPattern = findConversionPattern(base,term);
        if(conversionPattern.equalsIgnoreCase("D")) {
            return baseAmount.multiply(currencyRates.getMap().get(base+term))
                    .setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        return null;
    }

    private String findConversionPattern(final String base, final String term) throws IOException, InvalidFormatException {
        //TODO: Handle exceptions here only
        List<String> baseCurrencies = new ArrayList<>();
        List<String> termsCurrencies = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        String conversionPattern;
        try (Workbook workbook = WorkbookFactory.create(ResourceUtils.getFile("classpath:matrix.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                baseCurrencies.add(StringUtils.trimAllWhitespace(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0))));
                termsCurrencies.add(StringUtils.trimAllWhitespace(dataFormatter.formatCellValue(sheet.getRow(0).getCell(i))));
            }
            int baseIndexInMatrix = baseCurrencies.indexOf(base);
            int termIndexInMatrix = termsCurrencies.indexOf(term);
            conversionPattern = StringUtils.trimAllWhitespace(dataFormatter.formatCellValue(sheet.getRow(baseIndexInMatrix).getCell(termIndexInMatrix)));
        }
        return conversionPattern;
    }
}
