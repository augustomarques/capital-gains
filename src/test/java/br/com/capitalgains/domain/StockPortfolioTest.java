package br.com.capitalgains.domain;

import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.BUY;
import static br.com.capitalgains.domain.TypeFinancialTransactionEnum.SELL;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class StockPortfolioTest {

    @Test
    void should_calculate_the_weight_average_price_of_the_purchase_operations() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(new FinancialTransaction(BUY, BigDecimal.TEN, 10, BigDecimal.ZERO));
        stockPortfolio.addTransaction(new FinancialTransaction(BUY, BigDecimal.valueOf(2), 50, BigDecimal.ZERO));
        stockPortfolio.addTransaction(new FinancialTransaction(SELL, BigDecimal.TEN, 20, BigDecimal.ZERO));

        final var weightedAveragePrice = stockPortfolio.getWeightedAveragePrice();

        assertThat(weightedAveragePrice).isEqualTo(BigDecimal.valueOf(3));
    }

    @Test
    void should_calculate_the_weight_average_price_as_zero_when_there_are_no_transactions() {
        final var stockPortfolio = new StockPortfolio();

        final var weightedAveragePrice = stockPortfolio.getWeightedAveragePrice();

        assertThat(weightedAveragePrice).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void should_return_the_taxes_in_the_order_they_are_registered() {
        final var stockPortfolio = new StockPortfolio();
        stockPortfolio.addTransaction(new FinancialTransaction(BUY, BigDecimal.TEN, 1000, BigDecimal.ZERO));
        stockPortfolio.addTransaction(
              new FinancialTransaction(SELL, BigDecimal.valueOf(20), 5000, BigDecimal.valueOf(10000)));
        stockPortfolio.addTransaction(new FinancialTransaction(SELL, BigDecimal.valueOf(5), 5000, BigDecimal.ZERO));

        final var taxes = stockPortfolio.getTaxes();

        assertThat(taxes)
              .hasSize(3)
              .containsExactly(BigDecimal.ZERO, BigDecimal.valueOf(10000), BigDecimal.ZERO);
    }

    @Test
    void should_calculate_loss() {
        final var stockPortfolio = new StockPortfolio();

        stockPortfolio.addLoss(BigDecimal.valueOf(1000));
        stockPortfolio.addLoss(BigDecimal.valueOf(2000));
        stockPortfolio.removeLoss(BigDecimal.valueOf(500));

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.valueOf(2500));
    }

    @Test
    void should_not_allow_negative_losses() {
        final var stockPortfolio = new StockPortfolio();

        stockPortfolio.addLoss(BigDecimal.valueOf(1000));
        stockPortfolio.removeLoss(BigDecimal.valueOf(1500));

        assertThat(stockPortfolio.getLoss()).isEqualTo(BigDecimal.ZERO);
    }
}
