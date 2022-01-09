package br.com.capitalgains.service.addmovement;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TaxRangeEnumTest {

    @Test
    void should_return_zero_for_tax_range() {
        final var taxPercentage = TaxRangeEnum.getTaxByValue(BigDecimal.valueOf(1000));

        assertThat(taxPercentage).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void should_return_twenty_percent_for_tax_range() {
        final var taxPercentage = TaxRangeEnum.getTaxByValue(BigDecimal.valueOf(25000));

        assertThat(taxPercentage).isEqualTo(BigDecimal.valueOf(0.2));
    }
}
