package br.com.capitalgains.calculator;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.capitalgains.domain.FinancialTransaction;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalculateWeightedAveragePriceTest {

    @Test
    void should_calculate_the_weighted_average_price() {
        final var finantialTransactions = List.of(
              new FinancialTransaction(BUY, BigDecimal.valueOf(100), 10, BigDecimal.ZERO),
              new FinancialTransaction(BUY, BigDecimal.valueOf(200), 20, BigDecimal.ZERO),
              new FinancialTransaction(SELL, BigDecimal.valueOf(500), 5, BigDecimal.ZERO));

        final var weightedAveragePrice = CalculateWeightedAveragePrice.calculate(finantialTransactions);

        assertThat(weightedAveragePrice).isEqualTo(BigDecimal.valueOf(167));
    }

    @Test
    void should_return_zero_when_there_are_no_transactions() {
        final var weightedAveragePrice = CalculateWeightedAveragePrice.calculate(List.of());

        assertThat(weightedAveragePrice).isEqualTo(BigDecimal.ZERO);
    }
}
