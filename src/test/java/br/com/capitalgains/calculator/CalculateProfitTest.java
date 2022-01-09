package br.com.capitalgains.calculator;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.capitalgains.domain.StockPortfolio;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CalculateProfitTest {

    @Test
    void should_not_change_profit_when_there_is_no_loss() {
        final var stockPortfolio = new StockPortfolio();

        final var profit = CalculateProfit.calculateProfitWithoutPriorLoss(stockPortfolio, BigDecimal.TEN);

        assertThat(profit).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void should_not_make_a_profit_when_the_loss_is_greater() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addLoss(BigDecimal.valueOf(100));

        final var profit = CalculateProfit.calculateProfitWithoutPriorLoss(stockPortfolio, BigDecimal.TEN);

        assertThat(profit).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void should_subtract_the_loss_from_the_profit() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addLoss(BigDecimal.valueOf(10));

        final var profit = CalculateProfit.calculateProfitWithoutPriorLoss(stockPortfolio, BigDecimal.valueOf(100));

        assertThat(profit).isEqualTo(BigDecimal.valueOf(90));
    }
}
