package com.anz.fxcalculator;

import com.anz.fxcalculator.service.ConverterService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FxcalculatorApplicationTests {

    @InjectMocks
    private FxcalculatorApplication target;

    @Mock
    private ConverterService converterService;

    @Test
    public void shouldRun() throws IOException, InvalidFormatException {
        when(converterService.convert(anyString(), anyString(), any(BigDecimal.class)))
                .thenReturn(new BigDecimal("100.00"));
        target.run("");

    }


}
